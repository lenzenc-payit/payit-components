package com.payit.components.validation.rules

import scalaz.Validation

trait StringOps {

  case class StartsWith(str: String) extends ValidationRule[String] {
    def apply(value: String): Validation[RuleViolation, String] = value match {
      case s if s != null && s.startsWith(str) => succeeded(value)
      case _ => failed("startswith", s"should start with $value", Vector(value))
    }
  }

}
