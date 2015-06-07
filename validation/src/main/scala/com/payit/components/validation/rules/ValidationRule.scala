package com.payit.components.validation.rules

import com.payit.components.validation.{Fail, Success, Validated}

trait ValidationRule[T] extends (T => Validated[RuleFailure, T]) {

  def succeeded(value: T): Validated[RuleFailure, T] = Success(value)

  def failed(ruleKey: String, message: String, params: Seq[String] = Seq.empty[String]): Validated[RuleFailure, T] = {
    Fail(RuleFailure(ruleKey, message, params))
  }

}
