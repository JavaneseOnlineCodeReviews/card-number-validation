package com.github.rcd27.cnv

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

interface CardInfoService {
    val baseUrl: String

    /**
     *  Connects to [baseUrl], makes a simple GET request and parses response body
     *  in search of JSON pairs for 'scheme' and 'brand' keys.
     *
     *  @param [cardNumber] String representation of the cad number
     *  @return a [CreditCard] data transfer object
     */
    fun getCardInfo(cardNumber: String): CreditCard

    data class CreditCard(
        var scheme: String?,
        var brand: String?
    )

    open class Real : CardInfoService {
        override val baseUrl: String = "https://lookup.binlist.net/"

        override fun getCardInfo(cardNumber: String): CreditCard {
            val result = CreditCard(null, null)

            makeRequest(cardNumber,
                { responseCode ->
                    when (responseCode) {
                        200 -> Unit
                        400 -> throw IllegalArgumentException("Bad request for card: $cardNumber")
                    }
                },
                { response ->
                    response.parse("scheme") {
                        result.scheme = it
                    }.parse("brand") {
                        result.brand = it
                    }
                })

            return result
        }

        protected open fun makeRequest(
            cardNumber: String,
            responseCodeConsumer: (Int) -> Unit,
            responseConsumer: (Response) -> Unit
        ) {
            val url = URL(baseUrl + cardNumber)
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                addRequestProperty("Accept-Version", "3")
            }

            println("Sending 'GET' request to $url")
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

            responseConsumer(Response.Real(response.toString()))
        }
    }
}