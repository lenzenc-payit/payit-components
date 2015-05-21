package com.payit.components.validation

import com.payit.components.validation.rules.RuleViolation

import scalaz.Validation

trait Validator[T] extends (T => Validation[Map[String, Seq[RuleViolation]], T])