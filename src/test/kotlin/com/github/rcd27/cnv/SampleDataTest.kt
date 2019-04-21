package com.github.rcd27.cnv

import com.google.common.truth.Truth
import org.junit.Test

class SampleDataTest {

    private val numberValidator = CardNumberValidator.Real()
    private val cardInfoService = CardInfoService.Real()

    @Test
    fun `Valid Visa`() {
        val cardNumber = "4929804463622139"
        val expectedCard = CardInfoService.CreditCard("visa", null)
        Truth.assertThat(numberValidator.isValid(cardNumber)).isTrue()
        val actualCardInfo = cardInfoService.getCardInfo(cardNumber)
        Truth.assertThat(actualCardInfo.scheme).isEqualTo(expectedCard.scheme)
    }

    @Test
    fun `Invalid Visa`() {
        val cardNumber = "4929804463622138"
        val expectedCard = CardInfoService.CreditCard("visa", null)
        Truth.assertThat(numberValidator.isValid(cardNumber)).isFalse()
        val actualCardInfo = cardInfoService.getCardInfo(cardNumber)
        Truth.assertThat(actualCardInfo.scheme).isEqualTo(expectedCard.scheme)
    }

    @Test
    fun `Valid Maestro`() { // FIXME: broken sample data in test-case
        val cardNumber = "6762765696545485"
        val expectedCard = CardInfoService.CreditCard("maestro", null)
        Truth.assertThat(numberValidator.isValid(cardNumber)).isTrue()
        val actualCardInfo = cardInfoService.getCardInfo(cardNumber)
        Truth.assertThat(actualCardInfo.scheme).isEqualTo(expectedCard.scheme)
    }

    @Test
    fun `Invalid MasterCard`() {
        val cardNumber = "5212132012291762"
        val expectedCard = CardInfoService.CreditCard("mastercard", null)
        Truth.assertThat(numberValidator.isValid(cardNumber)).isFalse()
        val actualCardInfo = cardInfoService.getCardInfo(cardNumber)
        Truth.assertThat(actualCardInfo.scheme).isEqualTo(expectedCard.scheme)
    }

    @Test
    fun `Valid ChinaUnionPay`() {
        val cardNumber = "6210948000000029" // FIXME: 404 from binlist response
        val expectedCard = CardInfoService.CreditCard("mastercard", null)
        Truth.assertThat(numberValidator.isValid(cardNumber)).isTrue()
        val actualCardInfo = cardInfoService.getCardInfo(cardNumber)
        Truth.assertThat(actualCardInfo.scheme).isEqualTo(expectedCard.scheme)
    }
}