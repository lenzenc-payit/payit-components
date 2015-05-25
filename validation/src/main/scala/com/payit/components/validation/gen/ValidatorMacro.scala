package com.payit.components.validation.gen

import MacroHelper._

import com.payit.components.validation.Validator

object ValidatorMacro {

  def apply[T : c.WeakTypeTag](c: Context)(f: c.Expr[T => Unit]): c.Expr[Validator[T]] = {

    import c.universe._

    val v = q"""
        new Validator[${weakTypeOf[T]}] {
          val ruleSets = Vector.empty[ValidationRuleSet[_,_]]
        }
    """
    c.Expr[Validator[T]](v)
  }

}
