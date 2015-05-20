package com.payit.components.validation

class DefaultValidator[T](ruleSets: ValidationRuleSet[T, _]*) extends Validator[T] {

  def apply(value: T): Validated[T] = Success(value)

}
