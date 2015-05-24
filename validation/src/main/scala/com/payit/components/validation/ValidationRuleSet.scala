package com.payit.components.validation

import com.payit.components.validation.rules.{RuleViolation, ValidationRule}

import scalaz._
import Scalaz._

trait ValidationRuleSet[T, V] extends (T => Validation[Vector[RuleViolation], T]) {
  def paramName: String
  def mapper: (T) => V
  def rules: Vector[ValidationRule[V]]

  def apply(obj: T): Validation[Vector[RuleViolation], T] = {
    var violations = Vector[RuleViolation]()
    rules.foreach { rule =>
      rule(mapper(obj)) match {
        case Failure(f) => {
          if (!violations.map(_.key).contains(f.key)) violations = violations :+ f
        }
        case _ => Nil
      }
    }
    if (violations.isEmpty) obj.success else violations.failure
  }

}
