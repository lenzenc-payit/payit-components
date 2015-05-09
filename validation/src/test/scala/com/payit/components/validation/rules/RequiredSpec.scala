package com.payit.components.validation.rules

import com.payit.components.validation.ValidationMatchers
import org.specs2.mutable.Specification

class RequiredSpec extends Specification with GeneralOps with ValidationMatchers {

  val requiredKey = "required"
  val blankKey = "blank"
  val requiredMsg = "is required"
  val blankMsg = "must not be blank"

  ".isValid" >> {
    "using default constructor" >> {
      val rule = Required[String]()
      "should allow values" >> {
        rule should passValues("value", " leadingValue", "trailingValue ", " leadingtrailingValue ")
      }
      "should not allow values" >> {
        rule should failValues(null, "", " ", "  ")
      }
      "when value is NULL" >> {
        "should have expected key" >> {
          val result = rule.isValid(null)
          result should beAnInstanceOf[Failed]
          result.asInstanceOf[Failed].key must_== requiredKey
        }
        "should have expected msg" >> {
          val result = rule.isValid(null)
          result should beAnInstanceOf[Failed]
          result.asInstanceOf[Failed].msg must_== requiredMsg
        }
      }
      "when value is blank" >> {
        "should have expected key" >> {
          val result = rule.isValid("")
          result should beAnInstanceOf[Failed]
          result.asInstanceOf[Failed].key must_== blankKey
        }
        "should have expected msg" >> {
          val result = rule.isValid("")
          result should beAnInstanceOf[Failed]
          result.asInstanceOf[Failed].msg must_== blankMsg
        }
      }
    }
    "using constructor with allowedBlank equals false" >> {
      val rule = Required[String](allowBlank = false)
      "should allow values" >> {
        rule should passValues("value", " leadingValue", "trailingValue ", " leadingtrailingValue ")
      }
      "should not allow values" >> {
        rule should failValues(null, "", " ", "  ")
      }
      "when value is NULL" >> {
        "should have expected key" >> {
          val result = rule.isValid(null)
          result should beAnInstanceOf[Failed]
          result.asInstanceOf[Failed].key must_== requiredKey
        }
        "should have expected msg" >> {
          val result = rule.isValid(null)
          result should beAnInstanceOf[Failed]
          result.asInstanceOf[Failed].msg must_== requiredMsg
        }
      }
      "when value is blank" >> {
        "should have expected key" >> {
          val result = rule.isValid("")
          result should beAnInstanceOf[Failed]
          result.asInstanceOf[Failed].key must_== blankKey
        }
        "should have expected msg" >> {
          val result = rule.isValid("")
          result should beAnInstanceOf[Failed]
          result.asInstanceOf[Failed].msg must_== blankMsg
        }
      }
    }
    "using constructor with allowedBlank equals true" >> {
      val rule = Required[String](allowBlank = true)
      "should allow values" >> {
        rule should passValues("value", "", " ", " leadingValue", "trailingValue ", " leadingtrailingValue ")
      }
      "should not allow values" >> {
        rule should failValues(null)
      }
      "when value is NULL" >> {
        "should have expected key" >> {
          val result = rule.isValid(null)
          result should beAnInstanceOf[Failed]
          result.asInstanceOf[Failed].key must_== requiredKey
        }
        "should have expected msg" >> {
          val result = rule.isValid(null)
          result should beAnInstanceOf[Failed]
          result.asInstanceOf[Failed].msg must_== requiredMsg
        }
      }
    }
  }

//  case class TestRule() extends ValidationRule[String] {
//    def isValid(value: String): Result = value match {
//      case "test" => Passed(value)
//      case _ => Failed("testKey", s"Failed with value: $value")
//    }
//  }

//  ".isValid" >> {
//    val rule = TestRule()
//    "allows" >> {
//      rule should passValues("test")
//    }
//    "does not allow" >> {
//      rule must failValues("1")
//    }
//  }

//  ".isValid" >> {
//    "using default constructor" >> {
//      Result.foreach(Seq("1", "2")) { value => value must_== "2" }
////      Result.foreach(Seq("1", "foo")) { value => value must_== "foo" }
//    }
//  }
//

//  def m1: Matcher[String] = { s: String =>
//    (s.startsWith("hello"), s+" doesn't start with hello")
//  }

//  ".isValid" >> {
//    "using default constructor" >> {
//      Fragment.foreach(Seq("dd")) { value => s"value $value should be allowed" >> pending }
//      Fragment.foreach(Seq("dd")) { value => s"value $value should not be allowed" >> pending }
//    }
//  }

//  ".isValid" >> {
//    "using default constructor" >> {
//      "rule" | "r" |
//      100 ! 200 |> { (v, r) =>
//        v must_== r
//      }
//    }
//  }

//  ".isValid" >> {
//    "using default constructor" >> {
//      Fragment.foreach(Seq("dd")) { value =>
//        s"value $value should be allowed" >> {
//          pending
//        }
//      }
//      Fragment.foreach(Seq("dd")) { value =>
//        s"value $value should not be allowed" >> {
//          pending
//        }
//      }
//    }
//    "using allowedBlank is false constructor" >> {
//      Fragment.foreach(Seq("dd")) { value =>
//        s"value $value should be allowed" >> {
//          pending
//        }
//      }
//      Fragment.foreach(Seq("dd")) { value =>
//        s"value $value should not be allowed" >> {
//          pending
//        }
//      }
//    }
//    "using allowedBlank is true constructor" >> {
//      Fragment.foreach(Seq("dd")) { value =>
//        s"value $value should be allowed" >> {
//          pending
//        }
//      }
//      Fragment.foreach(Seq("dd")) { value =>
//        s"value $value should not be allowed" >> {
//          pending
//        }
//      }
//    }
//  }

//  ".valid" >> {
//    "using default constructor" >> {
//      "NULL value not be allowed" >> pending
//      "not NULL value should not be allowed" >> pending
//      "empty string value should not be allowed" >> pending
//      "value with trailing spaces should be allowed" >> pending
//      "value with leading speaces shold be allowed" >> pending
//      "value with both leading and trailing spaces should be allowed" >> pending
//      "value with all spaces should not be allowed" >> pending
//    }
//    "using allowedBlank is false constructor" >> {
//      "NULL value not be allowed" >> pending
//      "not NULL value should not be allowed" >> pending
//      "empty string value should not be allowed" >> pending
//      "value with trailing spaces should be allowed" >> pending
//      "value with leading speaces shold be allowed" >> pending
//      "value with both leading and trailing spaces should be allowed" >> pending
//      "value with all spaces should not be allowed" >> pending
//    }
//    "using allowedBlank is true constructor" >> {
//      "NULL value not be allowed" >> pending
//      "not NULL value should not be allowed" >> pending
//      "empty string value should be allowed" >> pending
//      "value with trailing spaces should be allowed" >> pending
//      "value with leading speaces shold be allowed" >> pending
//      "value with both leading and trailing spaces should be allowed" >> pending
//      "value with all spaces should be allowed" >> pending
//    }
//  }

//  Piple in test variables into one test; http://stackoverflow.com/questions/6805267/scalatest-or-specs2-with-multiple-test-cases/6805745#6805745

//  ".isValid" >> {
//    "when value is NULL" >> {
//      "using default constructor" >> {
//        "should not be allowed" >> pending
//      }
//      "using allowedBlank is true constructor" >> {
//        "should not be allowed" >> pending
//      }
//      "using allowedBlank is false constructor" >> {
//        "should not be allowed" >> pending
//      }
//    }
//    "when value is Not NULL" >> {
//      "using default constructor" >> {
//        "should be allowed" >> pending
//      }
//      "using allowedBlank is true constructor" >> {
//        "should be allowed" >> pending
//      }
//      "using allowedBlank is false constructor" >> {
//        "should be allowed" >> pending
//      }
//    }
//    "when value is empty string" >> pending
//    "when value has trailing spaces" >> pending
//    "when value has leading spaces" >> pending
//    "when value has trailing and leading spaces" >> pending
//  }
//
//  ".isValid" >> {
//    "using default constructor" >> {
//      "when value is null"
////      "should not allow null value" >> pending
////      "should allow a not null value" >> pending
////      "should not allow" >> pending
////      "should not allow a blank value when a String value has"
//    }
//    "when allowedBlank is set to false" >> pending
//    "when allowedBlank is set to true" >> pending
//  }

}
