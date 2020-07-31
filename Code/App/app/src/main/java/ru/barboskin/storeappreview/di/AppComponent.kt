package ru.barboskin.storeappreview.di

import android.content.Context
import ru.barboskin.storeappreview.di.modules.CategoriesModule
import ru.barboskin.storeappreview.di.modules.NetworkModule
import ru.barboskin.storeappreview.di.modules.ReviewModule
import ru.barboskin.storeappreview.domain.CategoriesRepository
import ru.barboskin.storeappreview.domain.ReviewsRepository

interface AppComponent {
    val categoriesRepository: CategoriesRepository
    val reviewsRepository: ReviewsRepository
}

fun AppComponent(context: Context): AppComponent {
    val networkModule = NetworkModule(context)
    return object : AppComponent,
        CategoriesModule by CategoriesModule(networkModule),
        ReviewModule by ReviewModule(networkModule) {}
}
