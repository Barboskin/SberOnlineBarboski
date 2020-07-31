package ru.barboskin.storeappreview.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import ru.barboskin.storeappreview.App
import ru.barboskin.storeappreview.di.AppComponent

inline fun <reified T : Any> Activity.startActivity() {
    startActivity(intentFor<T>(this))
}

inline fun <reified T : Any> Activity.startActivity(
    options: Bundle? = null,
    extras: Intent.() -> Unit = { }
) {
    val intent = intentFor<T>(this)
    intent.extras()
    startActivity(intent, options)
}

fun Activity.getComponent(): AppComponent {
    return (applicationContext as App).component
}
