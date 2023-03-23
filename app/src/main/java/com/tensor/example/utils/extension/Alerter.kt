package com.tensor.example.utils.extension

import android.app.Activity
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.Fragment
import com.tapadoo.alerter.Alerter
import com.tensor.example.R

/**
 *  Extension for alert display
 *
 * */
fun Activity.showError(message: String = "", title: String = "") {
    Alerter.create(this).setTitle(title).setText(message)
        .setIcon(R.drawable.ic_error)
        .setBackgroundColorRes(R.color.color_error).show()
}

/**
 *  Extension for success alert display
 *
 * */
fun Activity.showSuccess(message: String = "", title: String = "") {
    Alerter.create(this).setTitle(title).setText(message)
        .setIcon(R.drawable.ic_done_all_black_24dp)
        .setBackgroundColorRes(R.color.color_sky_blue).show()
}

/**
 *  Extension for alert display
 *
 * */
fun Fragment.showError(message: String = "", title: String = "") {
    activity?.showError(message, title)
}

fun showErrorFragment(view: AppCompatDialog?, message: String = "", title: String = "") {
    view?.let {
        Alerter.create(view).setTitle(title).setIcon(R.drawable.ic_error)
            .setBackgroundColorRes(R.color.color_error).setText(message).show()
    }
}

fun Fragment.showSuccess(message: String = "", title: String = "") {
    activity?.showSuccess(message, title)
}
