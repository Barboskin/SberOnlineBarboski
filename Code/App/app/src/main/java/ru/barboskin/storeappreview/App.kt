package ru.barboskin.storeappreview

import android.app.Application
import ru.barboskin.storeappreview.di.AppComponent

class App : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = AppComponent(this)
    }
}
