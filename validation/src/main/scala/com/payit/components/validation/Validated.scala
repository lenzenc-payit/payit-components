package com.payit.components.validation

sealed trait Validated[+A, +B] {

  def isSuccess: Boolean = this match {
    case Success(_) => true
    case Fail(_) => false
  }

  def isFail: Boolean = this match {
    case Success(_) => false
    case Fail(_) => true
  }

}

final case class Fail[A](a: A) extends Validated[A, Nothing]
final case class Success[B](b: B) extends Validated[Nothing, B]
