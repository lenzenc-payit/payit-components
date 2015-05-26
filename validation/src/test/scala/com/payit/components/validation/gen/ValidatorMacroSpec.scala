package com.payit.components.validation.gen

import org.specs2.mutable.Specification

class ValidatorMacroSpec extends Specification {

  import com.payit.components.validation._

  case class Person(name: String, age: Int)

  ".apply" >> {
    val validator = validations[Person] { p =>
      p.name.is(MaxLength(2))
    }
    println(validator(Person("Craig", 39)))
    success
  }

}
