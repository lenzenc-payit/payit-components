package com.payit.components

import com.payit.components.validation.gen.ValidatorMacro
import com.payit.components.validation.rules.GeneralOps

import language.experimental.macros

package object validation extends GeneralOps {

  def validations[T](f: T => Unit): Validator[T] = macro ValidatorMacro.apply[T]

}
