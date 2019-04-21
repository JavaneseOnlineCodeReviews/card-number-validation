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

class CardNumberValidatorTest {

    private var validator: CardNumberValidator by Delegates.notNull()

    @Before
    fun setUp() {
        validator = CardNumberValidator.Real()
    }

    @Test
    fun main() {
        val validationResult = validator.isValid(validCardNumber)
        Truth.assertThat(validationResult).isTrue()
    }

    @Test
    fun `contains no chars`() {
        val cardNumber = validCardNumber.replace('0', 'x')
        val validationResult = validator.isValid(cardNumber)
        Truth.assertThat(validationResult).isFalse()
    }

    @Test
    fun `contains no whitespaces`() {
        val cardNumber = validCardNumber.replace('5', ' ')
        val validationResult = validator.isValid(cardNumber)
        Truth.assertThat(validationResult).isFalse()
    }

    @Test
    fun `contains no non-digits`() {
        val cardNumber = validCardNumber.replace('5', '+').replace('6', '-')
        val validationResult = validator.isValid(cardNumber)
        Truth.assertThat(validationResult).isFalse()
    }

    @Test
    fun `not starting with 0`() {
        val cardNumber = validCardNumber.replaceFirst('5', '0')
        val validationResult = validator.isValid(cardNumber)
        Truth.assertThat(validationResult).isFalse()
    }

    @Test
    fun `contains between 12 and 19 digits`() {
        val validationResultLess = validator.isValid("55369137797")
        Truth.assertThat(validationResultLess)
            .isFalse()

        val cardNumberBetween = validCardNumber
        val validationResultBetween = validator.isValid(cardNumberBetween)
        Truth.assertThat(validationResultBetween).isTrue()

        val validationResultMore = validator.isValid("55369137797059342524")
        Truth.assertThat(validationResultMore)
            .isFalse()
    }

    @Test
    fun `Luhn check for valid card number`() {
        Truth.assertThat(validator.isValid(validCardNumber)).isTrue()
    }

    @Test
    fun `Luhn check for invalid card number`() {
        Truth.assertThat(validator.isValid("5536913779705933"))
            .isFalse()
    }
}