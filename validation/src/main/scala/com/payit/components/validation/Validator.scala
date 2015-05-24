package com.payit.components.validation

import com.payit.components.validation.rules.RuleViolation

import scalaz._
import Scalaz._

trait Validator[T] extends (T => Validation[ValidationFailure, T]) {

  def ruleSets: Vector[ValidationRuleSet[T, _]]

  def apply(obj: T): Validation[ValidationFailure, T] = {
    var failures = Map[String,Vector[RuleViolation]]()
    ruleSets.foreach { ruleSet =>
      ruleSet(obj) match {
        case Success(_) => Nil
        case Failure(f) => {
          failures = failures + (
            ruleSet.paramName ->
            concat(failures.getOrElse(ruleSet.paramName, Vector.empty[RuleViolation]), f)
          )
        }
      }

    }
    if (failures.isEmpty) obj.success else ValidationFailure(failures).failure
  }

  private def concat(v1: Vector[RuleViolation], v2: Vector[RuleViolation]): Vector[RuleViolation] = {
    val keys = v1.map(_.key)
    val toAdd = v2.filter { v => !keys.contains(v.key) }
    v1 ++ toAdd
  }

}