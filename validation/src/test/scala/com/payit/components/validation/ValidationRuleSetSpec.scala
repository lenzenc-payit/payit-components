package com.payit.components.validation

import com.payit.components.validation.rules.{RuleViolation, GeneralRules, ValidationRule}
import org.specs2.mutable.Specification

import scalaz._

class ValidationRuleSetSpec extends Specification with ValidationMatchers with GeneralRules {

  case class TestModel(v1: String = "Value1", v2: String = "Value2")

  ".apply" >> {
    "when validation is successful" >> {
      "it should return Success" >> {
        val model = TestModel()
        val ruleSet = buildRuleSet(Vector(Required()), model)
        ruleSet(model) should beSuccessful
      }
      "it should return expected TestModel object" >> {
        val model = TestModel()
        val ruleSet = buildRuleSet(Vector(Required()), model)
        ruleSet(model) should beSuccessful(model)
      }
    }
    "when validation fails" >> {
      "it should return Failure" >> {
        val model = TestModel()
        val ruleSet = buildRuleSet(Vector(MaxLength(2)), model)
        ruleSet(model) should beFailing
      }
      "it should return correct RuleViolation" >> {
        val violations = failureRun(Vector(MaxLength(2)))
        violations.map(_.key) must contain(exactly("maxlength"))
      }
      "for 2 rules" >> {
        "it should return correct 2 RuleViolations" >> {
          val violations = failureRun(Vector(MaxLength(2), StartsWith("failed")))
          violations.map(_.key) must contain(exactly("maxlength", "startswith"))
        }
      }
      "when there are multiple of the same rule types" >> {
        "it should return only 1 RuleViolation" >> {
          val violations = failureRun(Vector(MaxLength(2), MaxLength(2)))
          violations.map(_.key) must contain(exactly("maxlength"))
        }
      }
      "when there are multiple and 2 are of the same type" >> {
        "it should return only 2 RuleViolations" >> {
          val violations = failureRun(Vector(MaxLength(2), MaxLength(2), StartsWith("failed")))
          violations.map(_.key) must contain(exactly("maxlength", "startswith"))
        }
      }
    }
  }

  private def successRun(ruleVector: Vector[ValidationRule[String]], model: TestModel = TestModel()): TestModel = {

    val result = buildRuleSet(ruleVector, model)(model)
    result should beSuccessful
    result match {
      case Success(m) => m
      case _ => sys.error("match should of be successful!")
    }

  }

  private def failureRun(ruleVector: Vector[ValidationRule[String]], model: TestModel = TestModel()): Vector[RuleViolation] = {

    val result = buildRuleSet(ruleVector, model)(model)
    result should beFailing
    result match {
      case Failure(f) => f
      case _ => sys.error("match should of be a failure!")
    }

  }

  private def buildRuleSet(ruleVector: Vector[ValidationRule[String]], model: TestModel = TestModel()): ValidationRuleSet[TestModel, String] = {
    new ValidationRuleSet[TestModel, String] {
      val paramName = "v1"
      val mapper: (TestModel) => String = { m => m.v1 }
      val rules = ruleVector
    }
  }

}
