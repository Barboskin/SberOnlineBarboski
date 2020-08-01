package ru.barboskin.storeappreview.ext

import android.content.Context
import android.content.Intent

inline fun <reified T : Any> intentFor(context: Context): Intent = Intent(context, T::class.java)
