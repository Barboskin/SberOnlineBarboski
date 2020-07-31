package ru.barboskin.storeappreview.ext

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.Disposable

fun Disposable.disposeOnDestroy(lifecycleOwner: LifecycleOwner) {
    lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) dispose()
        }
    })
}
