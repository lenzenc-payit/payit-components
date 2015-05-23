package com.payit.components.validation

import com.payit.components.validation.rules.RuleViolation
import org.joda.time.DateTime

case class ValidationFailure(failures: Map[String, Vector[RuleViolation]])
