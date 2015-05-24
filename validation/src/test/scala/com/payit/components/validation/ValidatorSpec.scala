package com.payit.components.validation

import com.payit.components.validation.rules.RuleViolation
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

import scalaz._

class ValidatorSpec extends Specification with ValidationMatchers with Mockito {

  case class TestModel(v1: String = "Value1", v2: String = "Value2")
  val model = TestModel()

  ".apply" >> {
    "when there are no failures" >> {
      "it should return object being validated" >> {
        val ruleSet = mock[ValidationRuleSet[TestModel, String]]
        ruleSet.apply(model) returns Success[TestModel](model)
        validator(ruleSet)(model) should beSuccessful(model)
      }
    }
    "when there are failures" >> {
      "it should return a map with expected key" >> {
        val ruleSet = mockFailedRuleSet("v1", "key1", "msg1", model)
        validator(ruleSet)(model) should beFailing.like { case failure =>
          failure.failures must haveKey("v1")
        }
      }
      "it should return a map with expected RuleViolation" >> {
        val ruleSet = mockFailedRuleSet("v1", "key1", "msg1", model)
        validator(ruleSet)(model) should beFailing.like { case failure =>
          val violations = failure.failures.get("v1")
          violations must beSome
          violations.get.map(_.key) must contain(exactly("key1"))
        }
      }
      "against multiple ValidationRuleSets" >> {
        val ruleSet1 = mockFailedRuleSet("v1", "key1", "msg1", model)
        val ruleSet2 = mockFailedRuleSet("v2", "key2", "msg1", model)
        "it should return a map with expected keys" >> {
          validator(ruleSet1, ruleSet2)(model) should beFailing.like { case failure =>
            failure.failures must haveKeys("v1", "v2")
          }
        }
        "it should return a map with expected RuleViolations" >> {
          validator(ruleSet1, ruleSet2)(model) should beFailing.like { case failure =>
            val violations1 = failure.failures.get("v1")
            val violations2 = failure.failures.get("v2")
            violations1 must beSome
            violations1.get.map(_.key) must contain(exactly("key1"))
            violations2 must beSome
            violations2.get.map(_.key) must contain(exactly("key2"))
          }
        }
      }
      "when multiple ValidationRuleSets have the same paramName" >> {
        val ruleSet1 = mockFailedRuleSet("v1", "key1", "msg1", model)
        "it should return a map with expected key" >> {
          val ruleSet2 = mockFailedRuleSet("v1", "key2", "msg1", model)
          validator(ruleSet1, ruleSet2)(model) should beFailing.like { case failure =>
            failure.failures must haveKeys("v1")
          }
        }
        "it should return a map with expected combines RuleViolations" >> {
          val ruleSet2 = mockFailedRuleSet("v1", "key2", "msg1", model)
          validator(ruleSet1, ruleSet2)(model) should beFailing.like { case failure =>
            val violations = failure.failures.get("v1")
            violations must beSome
            violations.get.map(_.key) must contain(exactly("key1", "key2"))
          }
        }
        "and there are duplicated RuleViolations" >> {
          "it should return a map with filtered out duplicates" >> {
            val ruleSet2 = mockFailedRuleSet("v1", "key1", "msg1", model)
            validator(ruleSet1, ruleSet2)(model) should beFailing.like { case failure =>
              failure.failures must haveKeys("v1")
              val violations = failure.failures.get("v1")
              violations must beSome
              violations.get.map(_.key) must contain(exactly("key1"))
            }
          }
        }
      }
    }
  }

  private def validator(sets: ValidationRuleSet[TestModel, String]*): Validator[TestModel] = {
    new Validator[TestModel] {
      val ruleSets = sets.toVector
    }
  }

  private def mockFailedRuleSet(paramName: String, key: String, msg: String, model: TestModel) = {
    val ruleSet = mock[ValidationRuleSet[TestModel, String]]
    ruleSet.paramName returns paramName
    ruleSet.apply(model) returns Failure[Vector[RuleViolation]](Vector(RuleViolation(key, msg)))
    ruleSet
  }

}
