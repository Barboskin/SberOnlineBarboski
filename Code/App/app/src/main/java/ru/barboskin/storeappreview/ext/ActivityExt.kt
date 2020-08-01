package ru.barboskin.storeappreview.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
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

inline fun <reified T : Any> Activity.startActivityForResult(
    requestCode: Int,
    options: Bundle? = null,
    extras: Intent.() -> Unit = { }
) {
    val intent = intentFor<T>(this)
    intent.extras()
    startActivityForResult(intent, requestCode, options)
}

fun Activity.getComponent(): AppComponent {
    return (applicationContext as App).component
}

fun Activity.setStatusBarColor(@ColorRes color: Int) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, color)
}
