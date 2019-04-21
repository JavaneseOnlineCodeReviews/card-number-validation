package com.github.rcd27.cnv

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class Response(private val responseBody: String) {

    fun parse(field: String, resultConsumer: (String) -> Unit): Response {
        resultConsumer("Parsed Field")
        return this
    }
}

interface CardInfoService {
    val baseUrl: String

    fun getCardInfo(cardNumber: String): CreditCard

    data class CreditCard(
        var x: String,
        var y: String
    )

    open class Real : CardInfoService {
        override val baseUrl: String = "https://lookup.binlist.net/"

        override fun getCardInfo(cardNumber: String): CreditCard {
            val result = CreditCard("x", "y")

            makeRequest(cardNumber) { responseCode ->
                when (responseCode) {
                    200 -> Unit
                    400 -> throw IllegalArgumentException("Bad request for card: $cardNumber")
                }
            }.parse("") {
                result.x = it
            }.parse("") {
                result.y = it
            }

            return result
        }

        protected open fun makeRequest(cardNumber: String, responseCodeConsumer: (Int) -> Unit): Response {
            val url = URL(baseUrl + cardNumber)
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                addRequestProperty("Accept-Version", "3")
            }

            println("Sending 'GET' request...")
            println("Response code: ${connection.responseCode}")
            responseCodeConsumer(connection.responseCode)
            println("Response message: ${connection.responseMessage}")

            val `in` = BufferedReader(InputStreamReader(connection.inputStream))
            var inputLine = `in`.readLine()
            val response = StringBuffer()

            while (inputLine != null) {
                response.append(inputLine)
                inputLine = `in`.readLine()
            }
            `in`.close()

            return Response(response.toString())
        }
    }
}