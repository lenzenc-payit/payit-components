package com.payit.components.validation.rules

trait ValidationRule[T] {

  def isValid(value: T): Result

}
