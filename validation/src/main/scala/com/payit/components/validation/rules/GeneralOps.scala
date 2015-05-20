package com.payit.components.validation.rules

trait GeneralOps {

  type HasLength = Any { def length: Int }

  case class Required[T](allowBlank: Boolean = false) extends ValidationRule[T] {
    def apply(value: T): Result = value match {
      case null => Failed("required", "is required")
      case _ => if (!allowBlank && value.toString.trim.isEmpty) Failed("blank", "must not be blank") else Passed[T](value)
    }
  }

  case class MaxLength[T <% HasLength](max: Int) extends ValidationRule[T] {

    import scala.language.reflectiveCalls

    require(max >= 0, "max can not be less than ZERO")

    def apply(value: T): Result = value match {
      case x if x != null && x.length > max => Failed("maxlength", s"maximum is $max characters", Vector(max.toString))
      case _ => Passed[T](value)
    }
  }

}
