package com.payit.components.validation.gen

import com.payit.components.validation.Validator
import MacroHelper._

import scala.language.experimental.macros

object ValidatorMacro {

  def apply[T : c.WeakTypeTag](c: Context)(f: c.Expr[T => Unit]): c.Expr[Validator[T]] = {
    new ValidatorGenerator[c.type, T](c, f).create
  }

}
