package com.payit.components.validation

import com.payit.components.validation.rules.RuleViolation

import scalaz._
import Scalaz._

class DefaultValidator[T](ruleSets: ValidationRuleSet[T, _]*) extends Validator[T] {

  def apply(value: T): Validation[Map[String, Seq[RuleViolation]], T] = value.success

}
