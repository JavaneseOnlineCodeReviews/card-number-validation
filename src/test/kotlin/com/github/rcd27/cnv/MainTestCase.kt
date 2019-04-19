package com.github.rcd27.cnv

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

/**
 * Assume that a credit card number is valid if:
 *      - it contains only numbers and no leading 0 +
 *      - it is 12-19 digits long +
 *      - it passes the Luhn check https://en.wikipedia.org/wiki/Luhn_algorithm.
 *          For credit card numbers, the Luhn check digit is the last digit of the sequence.
 *
 * In addition, connect to https://binlist.net API and put additional information about credit card
 * in response to user of your framework. You are free to choose information about the card number,
 * which you consider necessary to return to the user.
 */

class MainTestCase {
    private var app: CNV by Delegates.notNull()

    @Before
    fun setUp() {
        app = CNV()
    }

    @Test
    fun main() {
        val cardNumber = "5536913779705934"
        val validationResult = app.validate(cardNumber)
        Truth.assertThat(validationResult).isEqualTo(ValidationResult.Success)
    }

    @Test
    fun `contains no chars`() {
        val cardNumber = "5536913x79705934"
        val validationResult = app.validate(cardNumber)
        Truth.assertThat(validationResult).isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)
    }

    @Test
    fun `contains no whitespaces`() {
        val cardNumber = "553691  79705934"
        val validationResult = app.validate(cardNumber)
        Truth.assertThat(validationResult).isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)
    }

    @Test
    fun `contains no non-digits`() {
        val cardNumber = "553691-7970593+"
        val validationResult = app.validate(cardNumber)
        Truth.assertThat(validationResult).isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)
    }

    @Test
    fun `not starting with 0`() {
        val cardNumber = "05536913579705934"
        val validationResult = app.validate(cardNumber)
        Truth.assertThat(validationResult).isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)
    }

    @Test
    fun `contains between 12 and 19 digits`() {
        val cardNumberLess = "1234567890"
        val validationResultLess = app.validate(cardNumberLess)
        Truth.assertThat(validationResultLess)
            .isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)

        val cardNumberBetween = "1234567890123"
        val validationResultBetween = app.validate(cardNumberBetween)
        Truth.assertThat(validationResultBetween).isInstanceOf(ValidationResult.Success::class.java)

        val cardNumberMore = "12345678901234567890"
        val validationResultMore = app.validate(cardNumberMore)
        Truth.assertThat(validationResultMore)
            .isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)
    }
}