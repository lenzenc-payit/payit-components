package com.payit.components.validation.rules

import scalaz._
import Scalaz._

trait GeneralOps {

  type HasLength = Any { def length: Int }

  case class Required[T](allowBlank: Boolean = false) extends ValidationRule[T] {
    def apply(value: T): Validation[RuleViolation, T] = value match {
      case null => RuleViolation("required", "is required").failure
      case _ => if (!allowBlank && value.toString.trim.isEmpty) RuleViolation("blank", "must not be blank").failure else value.success
    }
  }

  case class MaxLength[T <% HasLength](max: Int) extends ValidationRule[T] {

    import scala.language.reflectiveCalls

    require(max >= 0, "max can not be less than ZERO")

    def apply(value: T): Validation[RuleViolation, T] = value match {
      case x if x != null && x.length > max => RuleViolation("maxlength", s"maximum is $max characters", Vector(max.toString)).failure
      case _ => value.success
    }
  }

}
