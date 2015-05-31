package com.payit.components.validation.rules

import scalaz.Validation

trait StringOps {

  type HasLength = Any { def length: Int }

  case class StartsWith(str: String) extends ValidationRule[String] {
    def apply(value: String): Validation[RuleViolation, String] = value match {
      case s if s != null && s.startsWith(str) => succeeded(value)
      case _ => failed("startswith", s"should start with $value", Vector(value))
    }
  }

  case class MaxLength[T <% HasLength](max: Int) extends ValidationRule[T] {

    import scala.language.reflectiveCalls

    require(max >= 0, "max can not be less than ZERO")

    def apply(value: T): Validation[RuleViolation, T] = value match {
      case x if x != null && x.length > max => failed("maxlength", s"maximum is $max characters", Vector(max.toString))
      case _ => succeeded(value)
    }
  }

}
