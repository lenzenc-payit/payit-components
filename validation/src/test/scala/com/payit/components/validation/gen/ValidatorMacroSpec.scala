package com.payit.components.validation.gen

import org.specs2.matcher.Matcher
import org.specs2.mutable.Specification

import com.payit.components.validation.rules.{RuleViolation, ValidationRule}
import com.payit.components.validation._

import scalaz.Validation

class ValidatorMacroSpec extends Specification {

  case class Person(name: String, age: Int)

  case class IntRule() extends ValidationRule[Int] {
    def apply(value: Int): Validation[RuleViolation, Int] = succeeded(value)
  }

  ".apply" >> {
    "when there is one param with one validation rule" >> {
      val validator = validations[Person] { p =>
        p.name.is(Required())
      }
      "it should have one rule set with expected one validation rule" >> {
        validator should haveRule("name", required)
      }
      "it should have expected paramName" >> {
        validator should haveRuleSet("name")
      }
    }
    "when there is one param with more than one validation rules" >> {
      val validator = validations[Person] { p =>
        p.name.is(Required(), MaxLength(255))
      }
      "it should have one rule set with expected two validation rule" >> {
        validator should haveRule("name", required, maxLength)
      }
      "it should have expected paramName" >> {
        validator should haveRuleSet("name")
      }
    }
    "when there are more than one params with one validation rule" >> {
      val validator = validations[Person] { p =>
        p.name.is(MaxLength(255))
        p.age.is(Required())
      }
      "it should have rules for rule set name" >> {
        validator should haveRule("name", maxLength)
      }
      "it should have rules for rule set age" >> {
        validator should haveRule("age", required)
      }
      "it should have expected paramNames" >> {
        validator should haveRuleSet("name", "age")
      }
    }
    "when there are more than one params with more than one validation rules" >> {
      val validator = validations[Person] { p =>
        p.name.is(Required(), MaxLength(255))
        p.age.is(Required(), IntRule())
      }
      "it should have rules for rule set name" >> {
        validator should haveRule("name", required, maxLength)
      }
      "it should have rules for rule set age" >> {
        validator should haveRule("age", required, intRule)
      }
      "it should have expected paramName" >> {
        validator should haveRuleSet("name", "age")
      }
    }
    "when there are more than on params what are the same with difference validation rules" >> {
      val validator = validations[Person] { p =>
        p.name.is(Required(), MaxLength(255))
        p.name.is(MaxLength(255))
      }
      "it should have expected rule for first rule set for name" >> {
        validator.ruleSets.head.rules.map(_.getClass.getName) should contain(required)
      }
      "it should have expected rule for second rule set for name" >> {
        validator.ruleSets.last.rules.map(_.getClass.getName) should contain(maxLength)
      }
      "it should have expected paramNames" >> {
        validator should haveRuleSet("name", "name")
      }
    }
  }

  def required: String = classOf[Required[String]].getName
  def maxLength: String = classOf[MaxLength[String]].getName
  def intRule: String = classOf[IntRule].getName

  def haveRule(paramName: String, ruleClassNames: String*): Matcher[Validator[_]] = { v: Validator[_] =>
    val ruleSets = v.ruleSets.map(_.paramName).filter(_ == paramName)
    if (ruleSets.size == 0 || ruleSets.size > 1) {
      ruleSets should contain(exactly(paramName))
    } else {
      v.ruleSets.filter(_.paramName == paramName).head.rules.map(_.getClass.getName) should contain(exactly(ruleClassNames: _*))
    }
  }

  def haveRuleSet(names: String*) = ((_:Validator[_]).ruleSets.map(_.paramName)) ^^ contain(exactly(names:_*))


}
