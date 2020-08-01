package ru.barboskin.storeappreview.di.modules

import android.content.Context
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import ru.barboskin.storeappreview.BuildConfig
import ru.barboskin.storeappreview.base.network.ReviewsApi
import ru.barboskin.storeappreview.base.network.ReviewsApiStub
import kotlin.LazyThreadSafetyMode.NONE

interface NetworkModule {

    val reviewsApi: ReviewsApi
}

fun NetworkModule(context: Context) = object : NetworkModule {

    override val reviewsApi: ReviewsApi by lazy(NONE) {
        if (BuildConfig.DEBUG) {
            ReviewsApiStub()
        } else {
            Retrofit.Builder()
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .baseUrl(backendBaseUrl)
                .build()
                .create(ReviewsApi::class.java)
        }
    }

    private val backendBaseUrl: String = "https:/vk.com/"

    private val rxJava2CallAdapterFactory: RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.create()

}
