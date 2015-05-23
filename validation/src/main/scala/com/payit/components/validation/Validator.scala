package com.payit.components.validation

import scalaz._
import Scalaz._

trait Validator[T] extends (T => Validation[ValidationFailure, T]) {

  def ruleSets: Vector[ValidationRuleSet[T, _]]

  def apply(obj: T): Validation[ValidationFailure, T] = obj.success

}