package com.payit.components.validation.gen

import MacroHelper._

trait MacroHelper[C <: Context] {

  protected val context: C

  import context.universe._
  import org.scalamacros.resetallattrs._

  def resetAttr(tree: Tree): Tree = context.resetAllAttrs(tree)

}

object MacroHelper {
  type Context = scala.reflect.macros.blackbox.Context
}
