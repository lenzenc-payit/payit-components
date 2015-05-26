package com.payit.components.validation.gen

import MacroHelper._
import com.payit.components.validation.RuleOpts
import com.payit.components.validation.rules.ValidationRule

trait ExpressionFinder[C <: Context] extends PatternHelper[C] with MacroHelper[C] {

  import context.universe._

  case class ValidationExpr(key: Tree, validationObject: Tree, rules: List[Tree])

  val valOptsTerm = typeOf[ RuleOpts[_] ].typeSymbol.name.toTermName
  val ruleType = typeOf[ValidationRule[_]]

  private def extractValidationObject( t: Tree ): List[ Tree ] = collectFromPattern(t) {
    case Apply( TypeApply( Select( _, `valOptsTerm` ), tpe :: Nil ), e :: Nil ) => e
  }

  private def extractValidationRule(t: Tree): List[Tree] = collectFromPattern(t) {
    case t if t.tpe <:< ruleType => t
  }

  object KeySelector {
    def unapplySeq(ouv: Tree): Option[Seq[Name]] = ouv match {
      case Select(_, selector) => Some(selector :: Nil)
      case Select(KeySelector(elements @ _*), selector) => Some( elements :+ selector )
      case _ => None
    }
  }

  object ValidationExprMatcher {

    private val validationRuleSetType = typeOf[Vector[ValidationRule[_]]]

    def unapply( expr: Tree ): Option[ValidationExpr] = expr match {
      case t if t.tpe <:< validationRuleSetType => {
        extractValidationObject(expr) match {
          case Nil =>
            context.abort(
              t.pos,
              s"Failed to extract object under validation from tree $t (raw=${showRaw(t)})")
          case validationObject :: Nil => {
            val key = validationObject match {
              case KeySelector(elements @ _*) => Literal(Constant(elements.mkString( "." )))
              case _ => Literal(Constant(validationObject.toString))
            }
            Some(ValidationExpr(
              key,
              validationObject,
              extractValidationRule(expr)))
          }
          case _ => context.abort(
            t.pos,
            s"Unexpected match case when matching validation object from tree $t (raw=${showRaw(t)})")
        }
      }
      case _ => None
    }

  }

}
