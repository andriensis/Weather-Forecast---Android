package it.massimilianomassaro.weatherforecast.core.extensions

import android.app.Activity
import android.app.AlertDialog
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import it.massimilianomassaro.weatherforecast.R

fun Fragment.showAlert(title: String, message: String, onDismiss: (() -> Unit)? = null) {
    val alertDialogBuilder = AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(getString(R.string.ok), null)

    onDismiss?.let {
        alertDialogBuilder.setOnDismissListener {
            onDismiss()
        }
    }

    alertDialogBuilder.show()
}

fun Fragment.hideKeyboard(activity: Activity) {
    val imm: InputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}