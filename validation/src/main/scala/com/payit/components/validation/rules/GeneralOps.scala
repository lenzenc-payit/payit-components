package com.payit.components.validation.rules

import scalaz._
import Scalaz._

trait GeneralOps {

  case class Required[T](allowBlank: Boolean = false) extends ValidationRule[T] {
    def apply(value: T): Validation[RuleViolation, T] = value match {
      case null => failed("required", "is required")
      case _ => if (!allowBlank && value.toString.trim.isEmpty) failed("blank", "must not be blank") else succeeded(value)
    }
  }

}
