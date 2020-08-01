package ru.barboskin.storeappreview.di

import android.content.Context
import ru.barboskin.storeappreview.di.modules.NetworkModule
import ru.barboskin.storeappreview.di.modules.ReviewModule
import ru.barboskin.storeappreview.di.modules.TeamsModule
import ru.barboskin.storeappreview.domain.ReviewsRepository
import ru.barboskin.storeappreview.domain.TeamsRepository
import ru.barboskin.storeappreview.reviews.edit.interactor.EditTeamsInteractor

interface AppComponent {
    val teamsRepository: TeamsRepository
    val reviewsRepository: ReviewsRepository
    val editTeamsInteractor: EditTeamsInteractor
}

fun AppComponent(context: Context): AppComponent {
    val networkModule = NetworkModule(context)
    return object : AppComponent,
        TeamsModule by TeamsModule(networkModule),
        ReviewModule by ReviewModule(networkModule) {}
}
