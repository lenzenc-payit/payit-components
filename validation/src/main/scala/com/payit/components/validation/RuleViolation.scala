package com.payit.components.validation

case class RuleViolation(key: String, message: String, params: Vector[String] = Vector.empty)
