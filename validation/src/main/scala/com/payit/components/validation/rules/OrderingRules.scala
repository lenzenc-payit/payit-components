package com.payit.components.validation.rules

import scalaz.Validation

trait OrderingRules {

  case class GreaterThan[T : Ordering](compareTo: T)(implicit ev: Ordering[ T ]) extends ValidationRule[T] {

    require(compareTo != null, "compareTo value can not be NULL")

    def apply(value: T): Validation[RuleViolation, T] = {
      if (value != null && ev.gt(value, compareTo)) succeeded(value) else failed(
        "greaterthan",
        s"should be greater than $compareTo",
        Vector(compareTo.toString))
    }

  }

}
