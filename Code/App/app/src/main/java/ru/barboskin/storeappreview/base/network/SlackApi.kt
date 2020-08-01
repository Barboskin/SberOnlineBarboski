package ru.barboskin.storeappreview.base.network

import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface SlackApi {

    @POST("services/T015J3HLXML/B018U74G588/LtrwSaOm46rG2LmX3Mm9t0L6")
    fun sendMessage(
        @Body message: SlackMessage
    ): Completable
}
