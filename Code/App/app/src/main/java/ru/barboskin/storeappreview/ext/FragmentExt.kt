package ru.barboskin.storeappreview.ext

import androidx.fragment.app.Fragment
import ru.barboskin.storeappreview.App
import ru.barboskin.storeappreview.di.AppComponent

fun Fragment.getComponent(): AppComponent {
    return (requireActivity().applicationContext as App).component
}
