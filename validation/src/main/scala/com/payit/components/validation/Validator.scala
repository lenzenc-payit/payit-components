package com.payit.components.validation

case class FailureKey(key: String, parentKey: ParentKey = ParentKey())

trait Validator[T] extends (T => Validated[Map[FailureKey, ValidationFailure], T]) {

  def ruleSets: Seq[ValidationRuleSet[T, _]]

  def apply(obj: T): Validated[Map[FailureKey, ValidationFailure], T] = {
    Success(obj)
//    var failures = Seq[ValidationFailure]()
//    ruleSets.foreach { ruleSet =>
//      ruleSet(obj) match {
//        case Success(_) => Nil
//        case Fail(f) => {
//          failures = failures ++ f
//        }
//      }
//
//    }
//    if (failures.isEmpty) Success(obj) else Fail(failures)
  }

  private def concat(v1: Seq[ValidationFailure], v2: Seq[ValidationFailure]): Seq[ValidationFailure] = {
    val keys = v1.map{ f => f.parentKey.toString + f.key}
    v1 ++ v2.filter { f => !keys.contains(f.parentKey.toString + f.key) }
  }

}