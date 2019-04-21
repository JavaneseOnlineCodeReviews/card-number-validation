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
 *          For credit card numbers, the Luhn check digit is the last digit of the sequence. +
 *
 * In addition, connect to https://binlist.net API and put additional information about credit card
 * in response to user of your framework. You are free to choose information about the card number,
 * which you consider necessary to return to the user.
 */

class MainTestCase {
    private val validCardNumber = "5536913779705934"
    private val invalidCardNumberLessThanTwelveDigits = "55369137797"
    private val invalidCardNumberMoreThanNineteenDigits = "55369137797059342524"

    private var app: CNV by Delegates.notNull()

    @Before
    fun setUp() {
        app = CNV()
    }

    @Test
    fun main() {
        val validationResult = app.validate(validCardNumber)
        Truth.assertThat(validationResult).isEqualTo(ValidationResult.Success)
    }

    @Test
    fun `contains no chars`() {
        val cardNumber = validCardNumber.replace('0', 'x')
        val validationResult = app.validate(cardNumber)
        Truth.assertThat(validationResult).isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)
    }

    @Test
    fun `contains no whitespaces`() {
        val cardNumber = validCardNumber.replace('5', ' ')
        val validationResult = app.validate(cardNumber)
        Truth.assertThat(validationResult).isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)
    }

    @Test
    fun `contains no non-digits`() {
        val cardNumber = validCardNumber.replace('5', '+').replace('6', '-')
        val validationResult = app.validate(cardNumber)
        Truth.assertThat(validationResult).isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)
    }

    @Test
    fun `not starting with 0`() {
        val cardNumber = validCardNumber.replaceFirst('5', '0')
        val validationResult = app.validate(cardNumber)
        Truth.assertThat(validationResult).isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)
    }

    @Test
    fun `contains between 12 and 19 digits`() {
        val cardNumberLess = invalidCardNumberLessThanTwelveDigits
        val validationResultLess = app.validate(cardNumberLess)
        Truth.assertThat(validationResultLess)
            .isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)

        val cardNumberBetween = validCardNumber
        val validationResultBetween = app.validate(cardNumberBetween)
        Truth.assertThat(validationResultBetween).isInstanceOf(ValidationResult.Success::class.java)

        val cardNumberMore = invalidCardNumberMoreThanNineteenDigits
        val validationResultMore = app.validate(cardNumberMore)
        Truth.assertThat(validationResultMore)
            .isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)
    }

    @Test
    fun `Luhn check for valid card number`() {
        Truth.assertThat(app.validate(validCardNumber)).isInstanceOf(ValidationResult.Success::class.java)
    }

    @Test
    fun `Luhn check for invalid card number`() {
        Truth.assertThat(app.validate("5536913779705933"))
            .isInstanceOf(ValidationResult.Error(IllegalArgumentException())::class.java)
    }
}