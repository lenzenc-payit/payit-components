package com.payit.components.validation.rules

case class RuleViolation(key: String, message: String, params: Vector[String] = Vector.empty[String])
