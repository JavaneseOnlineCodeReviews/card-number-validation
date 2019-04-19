package com.github.rcd27.cnv

sealed class ValidationResult() {
    object Success : ValidationResult()
    data class Error(val error: Throwable) : ValidationResult()
}

class CNV {

    fun validate(cardNumber: Long): ValidationResult {

        return ValidationResult.Success
    }
}