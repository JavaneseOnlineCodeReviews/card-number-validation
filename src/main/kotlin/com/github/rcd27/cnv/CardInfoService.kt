package com.github.rcd27.cnv

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


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

fun main() {
    val url = URL("https://lookup.binlist.net/45717360")
    val connection = (url.openConnection() as HttpURLConnection).apply {
        requestMethod = "GET"
        addRequestProperty("Accept-Version", "3")
    }

    println("Sending 'GET' request...")
    println("Response code: ${connection.responseCode}")
    println("Response message: ${connection.responseMessage}")

    val `in` = BufferedReader(InputStreamReader(connection.inputStream))
    var inputLine = `in`.readLine()
    val response = StringBuffer()

    while (inputLine != null) {
        response.append(inputLine)
        inputLine = `in`.readLine()
    }
    `in`.close()

    println("Response: $response")

}