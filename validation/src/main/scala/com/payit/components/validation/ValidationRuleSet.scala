package com.payit.components.validation

import com.payit.components.validation.rules.{RuleViolation, ValidationRule}

import scalaz._
import Scalaz._

trait ValidationRuleSet[T, V] extends (T => Validation[Vector[RuleViolation], T]) {
  def paramName: String
  def mapper: (T) => V
  def rules: Vector[ValidationRule[V]]

  def apply(obj: T): Validation[Vector[RuleViolation], T] = obj.success

}
