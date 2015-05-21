package com.payit.components.validation

import com.payit.components.validation.rules.RuleViolation

class DefaultValidator[T](ruleSets: ValidationRuleSet[T, _]*) extends Validator[T] {

  def apply(value: T): Either[Map[String, Seq[RuleViolation]], T] = Right(value)

}
