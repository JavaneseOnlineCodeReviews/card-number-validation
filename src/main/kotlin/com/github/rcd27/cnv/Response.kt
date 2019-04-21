package com.github.rcd27.cnv

import java.util.regex.Pattern


interface Response {
    val responseBody: String

    fun parse(field: String, resultConsumer: (String) -> Unit): Response

    class Real(override val responseBody: String) : Response {
        override fun parse(field: String, resultConsumer: (String) -> Unit): Response {
            val pattern = Pattern.compile("(\"$field\"):(\"\\w+\")")
            val matcher = pattern.matcher(responseBody)
            while (matcher.find()) {
                resultConsumer(matcher.group(2).replace("\"", ""))
            }
            return this
        }
    }
}