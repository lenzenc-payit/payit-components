package com.payit.components.validation.gen

import org.specs2.mutable.Specification

class ValidatorMacroSpec extends Specification {

  import com.payit.components.validation._

  case class Person(name: String, age: Int)

  ".apply" >> {
    "when there is one param with one validation rule" >> {
      val validator = validations[Person] { p =>
        p.name.is(Required())
      }
      "it should have one rule set with expected one validation rule" >> pending
      "it should have expected paramName" >> {
        validator.ruleSets.head.paramName must_== "name"
      }
    }
    "when there is one param with more than one validation rules" >> {
      "it should have one rule set with expected two validation rule" >> pending
      "it should have expected paramName" >> pending
    }
    "when there are more than one params with one validation rule" >> {
      "it should have two rule sets with expected one validation rules each" >> pending
      "it should have expected paramNames" >> pending
    }
    "when there are more than one params with more than one validation rules" >> {
      "it should have two rule sets with expected two validation rules each" >> pending
      "it should have expected paramName" >> pending
    }
    "when there is code in the mapping closure other than validation expressions" >> {
      "it should have one rule set with expected one validation rule" >> pending
      "it should have expected paramName" >> pending
    }
  }

}
