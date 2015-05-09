package com.payit.components.validation.rules

sealed trait Result
final case class Passed[T](value: T) extends Result
final case class Failed(key: String, msg: String, params: Vector[String] = Vector.empty) extends Result