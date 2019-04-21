package com.github.rcd27.cnv

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates

class ResponseTest {

    private var testResponse: Response by Delegates.notNull()

    @Before
    fun setUp() {
        testResponse =
            Response.Real(
                "{\"number\":{}," +
                        "\"scheme\":\"mastercard\"," +
                        "\"type\":\"credit\"," +
                        "\"brand\":\"New World Immediate Debit\"," +
                        "\"country\":{\"numeric\":\"643\",\"alpha2\":\"RU\",\"name\":\"Russian Federation\",\"emoji\":\"\uD83C\uDDF7\uD83C\uDDFA\",\"currency\":\"RUB\",\"latitude\":60,\"longitude\":100}," +
                        "\"bank\":{}}"
            )
    }

    @Test
    fun `Find value for scheme`() {
        testResponse.parse("scheme") {
            Truth.assertThat(it).isEqualTo("mastercard")
        }
    }
}