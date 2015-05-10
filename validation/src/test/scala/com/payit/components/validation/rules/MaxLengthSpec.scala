package com.payit.components.validation.rules

import com.payit.components.validation.ValidationMatchers
import org.specs2.mutable.Specification

class MaxLengthSpec extends Specification with GeneralOps with ValidationMatchers {

  ".isValid" >> {
    val rule = MaxLength[String](max = 2)
    "should allow values" >> {
      rule should passValues("a", "ab", null, "")
    }
    "should not allow values" >> {
      rule should failValues("abc")
    }
    "when validation fails" >> {
      "it should have expected key" >> {
        val result = rule.isValid("abc")
        result should beAnInstanceOf[Failed]
        result.asInstanceOf[Failed].key must_== "maxlength"
      }
      "it should have expected msg" >> {
        val result = rule.isValid("abc")
        result should beAnInstanceOf[Failed]
        result.asInstanceOf[Failed].msg must_== "maximum is 2 characters"
      }
      "it should have expected params" >> {
        val result = rule.isValid("abc")
        result should beAnInstanceOf[Failed]
        result.asInstanceOf[Failed].params.size must_== 1
        result.asInstanceOf[Failed].params(0) must_== "2"
      }
    }
    "when max is set to ZERO" >> {
      "it should fail validation when value is greater than ZERO" >> {
        MaxLength[String](max = 0) should failValues("a")
      }
    }
    "when max is set to NON ZERO" >> {
      "it should error out" >> {
        MaxLength[String](max = -1) must throwA[IllegalArgumentException](message = "max can not be less than ZERO")
      }
    }
  }

}
