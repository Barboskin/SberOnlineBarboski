package ru.barboskin.storeappreview.reviews

import androidx.annotation.DrawableRes
import ru.barboskin.storeappreview.R

class PlatformIconProvider : (Boolean) -> Int {

    @DrawableRes
    override fun invoke(isApple: Boolean): Int {
        return if (isApple) R.drawable.ic_apple else R.drawable.ic_android
    }
}
