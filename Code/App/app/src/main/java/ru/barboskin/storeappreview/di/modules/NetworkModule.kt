package ru.barboskin.storeappreview.di.modules

import android.content.Context
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.barboskin.storeappreview.BuildConfig
import ru.barboskin.storeappreview.base.network.ReviewsApi
import ru.barboskin.storeappreview.base.network.ReviewsApiStub
import ru.barboskin.storeappreview.base.network.SlackApi
import kotlin.LazyThreadSafetyMode.NONE


interface NetworkModule {

    val reviewsApi: ReviewsApi
    val slackApi: SlackApi
}

fun NetworkModule(context: Context) = object : NetworkModule {

    override val reviewsApi: ReviewsApi by lazy(NONE) {
        if (BuildConfig.DEBUG) {
            ReviewsApiStub()
        } else {
            Retrofit.Builder()
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .baseUrl(backendBaseUrl)
                .addConverterFactory(gsonConverterFactory)
                .build()
                .create(ReviewsApi::class.java)
        }
    }

    override val slackApi: SlackApi by lazy(NONE) {
        Retrofit.Builder()
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(slackBaseUrl)
            .build()
            .create(SlackApi::class.java)
    }

    private val slackBaseUrl: String = "https://hooks.slack.com/"

    private val backendBaseUrl: String = "https:/vk.com/"

    private val rxJava2CallAdapterFactory: RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.create()

    private val gson = GsonBuilder().create()

    private val gsonConverterFactory = GsonConverterFactory.create(gson)
}
