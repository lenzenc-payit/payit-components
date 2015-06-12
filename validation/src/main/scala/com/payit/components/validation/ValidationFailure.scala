package com.payit.components.validation

case class ValidationFailure(parentKey: ParentKey, key: String, ruleKey: String, message: String, params: Seq[String] = Seq.empty)
