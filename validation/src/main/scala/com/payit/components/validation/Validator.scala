package com.payit.components.validation

import com.payit.components.validation.rules.RuleViolation

trait Validator[T] extends (T => Either[Map[String, Seq[RuleViolation]], T])