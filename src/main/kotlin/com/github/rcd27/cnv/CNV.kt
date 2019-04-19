package com.github.rcd27.cnv

import java.util.regex.Pattern

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val error: Throwable) : ValidationResult()
}

class CNV {

    fun validate(cardNumber: String): ValidationResult {
        return if (matchesToTheRule(cardNumber)) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(IllegalArgumentException("Bad card number: $cardNumber"))
        }
    }

    private fun matchesToTheRule(cardNumber: String): Boolean {
        val charPattern = Pattern.compile("^[^0](\\d{12,18})")
        return charPattern.matcher(cardNumber).matches()
    }
}