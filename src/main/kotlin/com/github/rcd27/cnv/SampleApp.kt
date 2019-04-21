package com.github.rcd27.cnv

fun main() {
    val numberValidator = CardNumberValidator.Real()
    val cardNumber = "5536913779705934"
    println("Checking card: $cardNumber")
    val cardNumberIsValid = numberValidator.isValid(cardNumber)
    println("Card number is valid: $cardNumberIsValid")
    if (cardNumberIsValid) {
        val cardInfoService = CardInfoService.Real()
        val cardInfo = cardInfoService.getCardInfo(cardNumber)
        println(cardInfo)
    } else {
        println("Card number [$cardNumber] didn't pass validation")
    }
}