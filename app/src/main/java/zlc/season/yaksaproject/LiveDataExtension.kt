package zlc.season.yaksaproject

import android.arch.lifecycle.MutableLiveData
import android.os.Looper.getMainLooper
import android.os.Looper.myLooper

fun <T> MutableLiveData<T>.update(t: T?) {
    if (myLooper() != getMainLooper()) {
        this.postValue(t)
    } else {
        this.value = t
    }
}