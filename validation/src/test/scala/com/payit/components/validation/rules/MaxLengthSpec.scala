package com.payit.components.validation.rules

import com.payit.components.validation.ValidationMatchers
import org.specs2.mutable.Specification

class MaxLengthSpec extends Specification with GeneralOps with ValidationMatchers {

  ".apply" >> {
    val rule = MaxLength[String](max = 2)
    "it should allow values" >> {
      rule should passValues("a", "ab", null, "")
    }
    "it should not allow values" >> {
      rule should failValues("abc")
    }
    "when validation fails" >> {
      "it should have expected RuleViolation" >> {
        rule("abc") should beLeft(RuleViolation("maxlength", "maximum is 2 characters", Vector("2")))
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
