package ru.barboskin.storeappreview.ext

import android.util.Log
import io.reactivex.Single
import io.reactivex.disposables.Disposable

private const val RX_JAVA_EXCEPTION = "RX_JAVA_EXCEPTION"

fun <T> Single<T>.subscribeBy(onSuccess: (T) -> Unit): Disposable {
    return subscribe(onSuccess) { Log.e(RX_JAVA_EXCEPTION, it.message) }
}
