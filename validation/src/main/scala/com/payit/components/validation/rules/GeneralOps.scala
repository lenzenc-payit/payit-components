package com.payit.components.validation.rules

trait GeneralOps {

  type HasLength = Any { def length: Int }

  case class Required[T](allowBlank: Boolean = false) extends ValidationRule[T] {
    def apply(value: T): Either[RuleViolation, T] = value match {
      case null => Left(RuleViolation("required", "is required"))
      case _ => if (!allowBlank && value.toString.trim.isEmpty) Left(RuleViolation("blank", "must not be blank")) else Right(value)
    }
  }

  case class MaxLength[T <% HasLength](max: Int) extends ValidationRule[T] {

    import scala.language.reflectiveCalls

    require(max >= 0, "max can not be less than ZERO")

    def apply(value: T): Either[RuleViolation, T] = value match {
      case x if x != null && x.length > max => Left(RuleViolation("maxlength", s"maximum is $max characters", Vector(max.toString)))
      case _ => Right(value)
    }
  }

}
