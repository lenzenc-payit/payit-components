package com.payit.components

import com.payit.components.validation.gen.ValidatorMacro
import com.payit.components.validation.rules.{StringOps, ValidationRule, GeneralOps}

import language.experimental.macros

package object validation extends GeneralOps with StringOps {

  def validations[T](f: T => Unit): Validator[T] = macro ValidatorMacro.apply[T]

  trait ValidationRuleContext[T] {

    def is(rules: ValidationRule[T]*): Vector[ValidationRule[T]] = rules.toVector

  }

  implicit class RuleOpts[T](value: T) extends ValidationRuleContext[T]

}
