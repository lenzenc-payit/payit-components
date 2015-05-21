package com.payit.components.validation.rules

import scalaz._
import Scalaz._

trait ValidationRule[T] extends (T => Validation[RuleViolation, T]) {

  def succeeded(value: T) = value.success

  def failed(key: String, message: String, params: Vector[String] = Vector.empty[String]) = {
    RuleViolation(key, message, params).failure
  }

}
