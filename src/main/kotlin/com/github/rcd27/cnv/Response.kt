package com.github.rcd27.cnv


interface Response {
    val responseBody: String

    fun parse(field: String, resultConsumer: (String) -> Unit): Response

    class Real(override val responseBody: String) : Response {
        override fun parse(field: String, resultConsumer: (String) -> Unit): Response {
            // TODO: implement
            resultConsumer("mastercard")
            return this
        }
    }
}