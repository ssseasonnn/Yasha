package zlc.season.yaksaproject

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import zlc.season.sange.Differ
import zlc.season.yasha.YashaItem
import java.util.concurrent.TimeUnit

class NormalItem(val i: Int, val text: String = "") : YashaItem {
    override fun toString() = "Item $i $text"

//    private val disposable: Disposable

    init {
        //Test auto clean up!!!!
//        disposable = Flowable.interval(0, 1, TimeUnit.SECONDS)
//                .subscribe {
//                    Log.d("YASHA", "$it")
//                }
    }

    override fun cleanUp() {
//        disposable.dispose()
    }

    override fun areContentsTheSame(other: Differ): Boolean {
        if (other !is NormalItem) return false
        return (other as NormalItem).text == text
    }

    override fun areItemsTheSame(other: Differ): Boolean {
        if (other !is NormalItem) return false
        return (other as NormalItem).i == i
    }

    override fun getChangePayload(other: Differ): Any? {
        if (other !is NormalItem) return null
        return (other as NormalItem).text
    }
}

class HeaderItem(val i: Int) : YashaItem {
    override fun toString() = "Header $i"
}

class FooterItem(val i: Int) : YashaItem {
    override fun toString() = "Footer $i"
}

class StateItem(val state: Int, val retry: () -> Unit) : YashaItem