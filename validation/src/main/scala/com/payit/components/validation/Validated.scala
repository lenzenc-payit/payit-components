package com.payit.components.validation

import org.joda.time.DateTime

sealed trait Validated[+T]
final case class Success[T](t: T) extends Validated[T]
final case class Failed(dateTime: DateTime, errors: Map[String,Seq[RuleViolation]]) extends Validated[Nothing]

