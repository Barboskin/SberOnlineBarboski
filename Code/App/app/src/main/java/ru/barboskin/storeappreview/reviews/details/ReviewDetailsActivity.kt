package ru.barboskin.storeappreview.reviews.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.barboskin.storeappreview.R
import ru.barboskin.storeappreview.ext.startActivity

class ReviewDetailsActivity : AppCompatActivity(R.layout.activity_review_details) {

    companion object {

        fun start(activity: AppCompatActivity) {
            activity.startActivity<ReviewDetailsActivity>()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
