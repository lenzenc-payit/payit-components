package com.payit.components.validation

import com.payit.components.validation.rules.ValidationRule

case class ValidationRuleSet[T](key: String, value: T, rules: ValidationRule[T]*)
