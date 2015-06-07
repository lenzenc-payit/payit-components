package com.payit.components.validation.rules

import org.specs2.mutable.Specification
import org.specs2.validation.ValidationMatchers

class RequiredSpec extends Specification with GeneralRules with ValidationMatchers {

  val requiredKey = "required"
  val blankKey = "blank"
  val requiredMsg = "is required"
  val blankMsg = "must not be blank"

  ".apply" >> {
    "using default constructor" >> {
      val rule = Required[String]()
      "it should allow values" >> {
        rule should passValues("value", " leadingValue", "trailingValue ", " leadingtrailingValue ")
      }
      "it should not allow values" >> {
        rule should failValues(null, "", " ", "  ")
      }
      "when value is NULL" >> {
        "it should have expected RuleViolation" >> {
          rule(null) should beFailing.like { case f =>
              f.ruleKey must_== requiredKey
              f.message must_== requiredMsg
          }
          rule(null) should beFailing(RuleFailure(requiredKey, requiredMsg))
        }
      }
      "when value is blank" >> {
        "it should have expected RuleViolation" >> {
          rule("") should beFailing(RuleFailure(blankKey, blankMsg))
        }
      }
    }
    "using constructor with allowedBlank equals false" >> {
      val rule = Required[String](allowBlank = false)
      "it should allow values" >> {
        rule should passValues("value", " leadingValue", "trailingValue ", " leadingtrailingValue ")
      }
      "it should not allow values" >> {
        rule should failValues(null, "", " ", "  ")
      }
      "when value is NULL" >> {
        "it should have expected RuleViolation" >> {
          rule(null) should beFailing(RuleFailure(requiredKey, requiredMsg))
        }
      }
      "when value is blank" >> {
        "it should have expected RuleViolation" >> {
          rule("") should beFailing(RuleFailure(blankKey, blankMsg))
        }
      }
    }
    "using constructor with allowedBlank equals true" >> {
      val rule = Required[String](allowBlank = true)
      "it should allow values" >> {
        rule should passValues("value", "", " ", " leadingValue", "trailingValue ", " leadingtrailingValue ")
      }
      "it should not allow values" >> {
        rule should failValues(null)
      }
      "when value is NULL" >> {
        "it should have expected RuleViolation" >> {
          rule(null) should beFailing(RuleFailure(requiredKey, requiredMsg))
        }
      }
    }
  }

}
