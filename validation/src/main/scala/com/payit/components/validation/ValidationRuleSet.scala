package com.payit.components.validation

import com.payit.components.validation.rules.ValidationRule

trait ValidationRuleSet[T,V] {
  def key: String
  def value(obj: T): V
  def rules: Vector[ValidationRule[V]]
}
