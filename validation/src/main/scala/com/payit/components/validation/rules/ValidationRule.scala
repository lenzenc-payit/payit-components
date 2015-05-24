package com.payit.components.validation.rules

import scalaz._
import Scalaz._

trait ValidationRule[T] extends (T => Validation[RuleViolation, T]) {

  def succeeded(value: T): Validation[RuleViolation, T] = value.success

  def failed(violation: RuleViolation): Validation[RuleViolation, T] = violation.failure

  def failed(key: String, message: String, params: Vector[String] = Vector.empty[String]): Validation[RuleViolation, T] = {
    failed(RuleViolation(key, message, params))
  }

}
