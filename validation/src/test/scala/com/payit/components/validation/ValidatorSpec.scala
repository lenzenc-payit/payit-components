package com.payit.components.validation

import org.specs2.mutable.Specification

class ValidatorSpec extends Specification {

  ".validate" >> {
    "when validation is successful" >> {
      "it should return expected class type" >> pending
      "return class should include reference to the object under validation" >> pending
    }
    "when validation fails" >> {
      "it should return expected class type" >> pending
      "it should contain date timestamp" >> pending
      "with one failure" >> {
        "it should contain map with expected key" >> pending
        "it should contain Seq of RuleViolation matching the expected failure" >> pending
      }
      "with multiple failures" >> {
        "it should have map with multiple expected keys" >> pending
        "it should contain Seq of RuleViolation matching the expected failures" >> pending
      }
      "with multiple rules violated" >> {
        "it should contain Seq of RuleViolations matching the expected failures" >> pending
      }
      "with multiple ValidationRuleSets with same keys" >> {
        "it should combine failures in one key and Seq of violations" >> pending
      }
      "with ValidationRuleSet that contains multiple of the same ValidationRule types" >> {
        "it should result in only one RuleViolation for that ValidationRule type" >> pending
      }
    }
  }

}
