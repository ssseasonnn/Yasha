package zlc.season.yaksaproject

import zlc.season.sange.Differ
import zlc.season.yasha.YashaItem

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
        return other.text == text
    }

    override fun areItemsTheSame(other: Differ): Boolean {
        if (other !is NormalItem) return false
        return other.i == i
    }

    override fun getChangePayload(other: Differ): Any? {
        if (other !is NormalItem) return null
        return other.text
    }
}

class HeaderItem(val i: Int, val text: String = "", var typeConflict: String? = null) : YashaItem {
    override fun typeConflict(): String? {
        return typeConflict
    }

    override fun toString() = "Header $i $text"

    override fun areItemsTheSame(other: Differ): Boolean {
        if (other !is HeaderItem) return false
        return other.i == i && other.typeConflict == typeConflict
    }

    override fun areContentsTheSame(other: Differ): Boolean {
        if (other !is HeaderItem) return false
        return other.text == text
    }

    override fun getChangePayload(other: Differ): Any? {
        if (other !is HeaderItem) return null
        return other.text
    }
}

class FooterItem(val i: Int) : YashaItem {
    override fun toString() = "Footer $i"
}

class StateItem(val state: Int, val retry: () -> Unit) : YashaItem