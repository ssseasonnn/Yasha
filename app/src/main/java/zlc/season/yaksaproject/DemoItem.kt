package zlc.season.yaksaproject

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import zlc.season.yasha.YashaItem
import java.util.concurrent.TimeUnit

class NormalItem(val i: Int) : YashaItem {
    override fun toString() = "Item $i"

    private val disposable: Disposable

    init {
        //Test auto clean up!!!!
        disposable = Flowable.interval(0, 1, TimeUnit.SECONDS)
                .subscribe {
                    Log.d("YASHA", "$it")
                }
    }

    override fun cleanUp() {
        disposable.dispose()
    }
}

class HeaderItem(val i: Int) : YashaItem {
    override fun toString() = "Header $i"
}

class FooterItem(val i: Int) : YashaItem {
    override fun toString() = "Footer $i"
}

class StateItem(val state: Int, val retry: () -> Unit) : YashaItem