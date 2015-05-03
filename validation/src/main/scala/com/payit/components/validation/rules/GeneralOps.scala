package com.payit.components.validation.rules

trait GeneralOps {

  case class Required[T](allowBlank: Boolean = false) extends ValidationRule[T] {
    def isValid(value: T): Result = value match {
      case null => Failed("required", "is required")
      case _ => if (!allowBlank && value.toString.trim.isEmpty) Failed("blank", "must not be blank") else Passed[T](value)
    }
  }

}
