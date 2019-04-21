package com.github.rcd27.cnv

import java.util.regex.Pattern


interface Response {
    val responseBody: String

    /**
     *  Parse [responseBody] in search of particular field
     *
     *  @param [field] JSON key to search in [responseBody]
     *  @return successful match to [resultConsumer], instance of [Response]
     */
    fun parse(field: String, resultConsumer: (String) -> Unit): Response

    class Real(override val responseBody: String) : Response {
        override fun parse(field: String, resultConsumer: (String) -> Unit): Response {
            val pattern = Pattern.compile("(\"$field\"):(\"[\\w\\s]+\")")
            val matcher = pattern.matcher(responseBody)
            while (matcher.find()) {
                resultConsumer(matcher.group(2).replace("\"", ""))
            }
            return this
        }
    }
}