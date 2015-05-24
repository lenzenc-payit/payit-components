package com.payit.components.validation

import com.payit.components.validation.rules.ValidationRule
import org.specs2.matcher.Matcher
import org.specs2.mutable.Specification

import scala.collection.mutable
import scalaz.Success

trait ValidationMatchers extends Specification with org.specs2.scalaz.ValidationMatchers {

  def passValues[T](values: T*): Matcher[ValidationRule[T]] = { rule: ValidationRule[T] =>
    var failed = mutable.Seq.empty[T]
    values.foreach { v =>
      rule(v) match {
        case Success(v) => Nil
        case _ => failed = failed :+ v
      }
    }
    (failed.isEmpty, s"Value(s): ${failed.toList} should pass validation for rule: ${rule.getClass.getSimpleName}")
  }

  def failValues[T](values: T*): Matcher[ValidationRule[T]] = { rule: ValidationRule[T] =>
    var passed = mutable.Seq.empty[T]
    values.foreach { v =>
      rule(v) match {
        case Success(_) => passed = passed :+ v
        case _ => Nil
      }
    }
    (passed.isEmpty, s"Value(s): ${passed.toList} should fail validation for rule: ${rule.getClass.getSimpleName}")
  }

}
