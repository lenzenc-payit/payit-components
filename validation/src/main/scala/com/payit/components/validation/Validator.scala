package com.payit.components.validation

case class Validator[T](ruleSets: ValidationRuleSet[_]*) {

  def validate(value: T): Validated[T] = {
    Success(value)
  }

}