package com.payit.components.validation

trait Validator[T] extends (T => Validated[T])