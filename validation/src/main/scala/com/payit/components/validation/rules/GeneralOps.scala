package com.payit.components.validation.rules

import scalaz._
import Scalaz._

trait GeneralOps {

  type HasLength = Any { def length: Int }

  case class Required[T](allowBlank: Boolean = false) extends ValidationRule[T] {
    def apply(value: T): Validation[RuleViolation, T] = value match {
      case null => failed("required", "is required")
      case _ => if (!allowBlank && value.toString.trim.isEmpty) failed("blank", "must not be blank") else succeeded(value)
    }
  }

  case class MaxLength[T <% HasLength](max: Int) extends ValidationRule[T] {

    import scala.language.reflectiveCalls

    require(max >= 0, "max can not be less than ZERO")

    def apply(value: T): Validation[RuleViolation, T] = value match {
      case x if x != null && x.length > max => failed("maxlength", s"maximum is $max characters", Vector(max.toString))
      case _ => value.success
    }
  }

}
