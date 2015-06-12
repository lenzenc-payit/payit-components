package com.payit.components.validation.rules

import org.specs2.mutable.Specification
import org.specs2.validation.ValidationMatchers

class StartsWithSpec extends Specification with StringRules with ValidationMatchers {

  ".apply" >> {
    val rule = StartsWith("Test")
    "it should allow values" >> {
      rule should passValues("Test", "Testing")
    }
    "it should not allow values" >> {
      rule should failValues(null, "Foo", "Tes")
    }
    "when successful" >> {
      "it should return value under test" >> {
        rule("Testing") should beSuccessful("Testing")
      }
    }
    "when fails" >> {
      "it should have correct rule violation ruleKey" >> {
        rule("foo") should beFailing.like { case f => f.ruleKey must_== "startswith" }
      }
      "it should have correct rule violation message" >> {
        rule("foo") should beFailing.like { case f => f.message must_== "should start with foo" }
      }
      "it should have correct rule violation params" >> {
        rule("foo") should beFailing.like { case f => f.params should contain(exactly("foo")) }
      }
    }
  }

}
