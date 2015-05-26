package com.payit.components.validation.gen

import MacroHelper._

trait PatternHelper[C <: Context] { self: MacroHelper[C] =>

  import context.universe._

  def collectFromPattern[R](tree: Tree)(pattern: PartialFunction[Tree, R]): List[R] = {
    var found: Vector[R] = Vector.empty
    new Traverser {
      override def traverse(subtree: Tree): Unit = {
        if (pattern.isDefinedAt(subtree)) {
          found = found :+ pattern(subtree)
        } else {
          super.traverse(subtree)
        }
      }
    }.traverse(tree)
    found.toList
  }

  type TransformAST = PartialFunction[ Tree, Tree ]

}
