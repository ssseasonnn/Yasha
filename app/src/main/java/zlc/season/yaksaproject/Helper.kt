package zlc.season.yaksaproject

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.content.res.Resources
import android.os.Looper.getMainLooper
import android.os.Looper.myLooper
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.FrameLayout

fun <T> MutableLiveData<T>.update(t: T?) {
    if (myLooper() != getMainLooper()) {
        this.postValue(t)
    } else {
        this.value = t
    }
}

fun Activity.toast(msg: String) {
    Snackbar.make(findViewById<FrameLayout>(android.R.id.content),
            msg, Snackbar.LENGTH_SHORT).show()
}

fun toast(view: View, msg: String) {
    Snackbar.make(view,
            msg, Snackbar.LENGTH_SHORT).show()
}

fun Activity.startActivity(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()