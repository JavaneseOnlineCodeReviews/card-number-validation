package com.github.rcd27.cnv

import java.util.regex.Pattern

interface CardNumberValidator {

    /**
     * ### Checks if given card number is valid:
     * 1. It contains only numbers and no leading 0
     * 2. It is 12-19 digits long
     * 3. It passes the [Luhn check](https://en.wikipedia.org/wiki/Luhn_algorithm.)
     *
     *  @param [cardNumber] String representation of the cad number
     *  @return *true* if all checks passed, otherwise - *false*
     */
    fun isValid(cardNumber: String): Boolean

    class Real : CardNumberValidator {
        override fun isValid(cardNumber: String): Boolean {
            return matchesToTheRule(cardNumber) && passesLuhnCheck(cardNumber)
        }

        private fun matchesToTheRule(cardNumber: String): Boolean {
            val charPattern = Pattern.compile("^[^0](\\d{12,18})")
            return charPattern.matcher(cardNumber).matches()
        }

        private fun passesLuhnCheck(cardNumber: String): Boolean {
            val lastDigit = cardNumber.last()
            val checkDigit = calculateCheckDigit(cardNumber.substring(0, cardNumber.length - 1))
            return lastDigit == checkDigit
        }

        private fun calculateCheckDigit(cardNumberWithoutLastDigit: String): Char {
            val digits = mutableListOf<Int>().apply {
                cardNumberWithoutLastDigit.asIterable().forEach { c ->
                    add(Character.getNumericValue(c))
                }
            }
            // Double every second digit starting from the end
            for (i in digits.size - 1 downTo 0 step 2) {
                digits[i] += digits[i]
                // Take greater than 10 and substract by 9
                if (digits[i] >= 10) {
                    digits[i] = digits[i] - 9
                }
            }
            val sum = digits.sum() * 9
            return sum.toString().last()
        }
    }
}