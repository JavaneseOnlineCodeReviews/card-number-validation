package com.github.rcd27.cnv

import com.google.common.truth.Truth
import org.junit.Test

/**
 * Assume that a credit card number is valid if:
 *      - it contains only numbers and no leading 0
 *      - it is 12-19 digits long
 *      - it passes the Luhn check https://en.wikipedia.org/wiki/Luhn_algorithm.
 *          For credit card numbers, the Luhn check digit is the last digit of the sequence.
 */

class MainTestCase {

    @Test
    fun main() {
        val app = CNV()
        val cardNumber = 5536_9137_7970_5934
        val validationResult = app.validate(cardNumber)
        Truth.assertThat(validationResult).isEqualTo(ValidationResult.Success)
    }
}