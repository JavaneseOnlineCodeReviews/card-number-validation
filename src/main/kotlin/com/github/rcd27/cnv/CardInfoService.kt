package com.github.rcd27.cnv

interface CardInfoService {

    fun getCardInfo(cardNumber: String): CreditCard

    data class CreditCard(
        val x: String,
        val y: String
    )

    class Real : CardInfoService {

        override fun getCardInfo(cardNumber: String): CreditCard {
            return CreditCard("x", "y")
        }
    }
}