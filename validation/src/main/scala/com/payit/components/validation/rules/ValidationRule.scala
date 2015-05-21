package com.payit.components.validation.rules

import scalaz.Validation

trait ValidationRule[T] extends (T => Validation[RuleViolation, T])
