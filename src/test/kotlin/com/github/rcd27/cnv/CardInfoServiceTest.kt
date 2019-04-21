package com.github.rcd27.cnv

import com.google.common.truth.Truth
import org.junit.Test

/**
 * In addition, connect to https://binlist.net API and put additional information about credit card
 * in response to user of your framework.
 * You are free to choose information about the card number, which you consider necessary to return to the user.
 */

class CardInfoServiceTest {

    @Test
    fun checkConnection() {
        val check200service = object : CardInfoService.Real() {
            override fun makeRequest(
                cardNumber: String,
                responseCodeConsumer: (Int) -> Unit,
                responseConsumer: (Response) -> Unit
            ) {
                super.makeRequest(
                    cardNumber,
                    { responseCode ->
                        Truth.assertThat(responseCode).isEqualTo(200)
                    },
                    {
                        // TODO: Handle response
                    })
            }
        }
        check200service.getCardInfo(validCardNumber)
    }
}