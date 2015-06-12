package com.payit.components.validation.gen

import MacroHelper._
import com.payit.components.validation.Validator

class ValidatorGenerator[C <: Context, T : C#WeakTypeTag](val context: C, v: C#Expr[T => Unit])
  extends ExpressionFinder[C] with MacroHelper[C]
{

  import context.universe._

  val Function(prototype :: prototypeTail, functionTree) = v.tree

  if (prototypeTail.nonEmpty) {
    context.abort(prototypeTail.head.pos, "Only single-parameter validators are supported!")
  }

  val rewriteValidationRuleSet: TransformAST = {
    case ValidationExprMatcher(ve: ValidationExpr) => {
      val rs = q"""
        new com.payit.components.validation.ValidationRuleSet[${weakTypeOf[ T ]},${ve.validationObject.tpe.typeSymbol}] {
          val parentKey = com.payit.components.validation.ParentKey()
          val key = ${resetAttrs(ve.key)}
          val mapper = { ${prototype} => ${resetAttrs(ve.validationObject)} }
          val rules = ${ve.rules.toVector}
        }
      """
      resetAttrs(rs)
    }
  }

  def create: Expr[Validator[T]] = {
    val ruleSets = collectFromPattern(functionTree)(rewriteValidationRuleSet)
    val validator = q"""
        new Validator[${weakTypeOf[T]}] {
          val ruleSets = ${ruleSets.toVector}
        }
      """
    context.Expr[ Validator[T] ](validator)
  }

}