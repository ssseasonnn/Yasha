package zlc.season.yaksa

import android.view.View

class YaksaItemWrapper<T>(val data: T, private val actualItem: YaksaItem) : YaksaItem {

    override fun render(position: Int, view: View) {
        actualItem.render(position, view)
    }

    override fun xml(): Int {
        return actualItem.xml()
    }

    override fun gridSpanSize(): Int {
        return actualItem.gridSpanSize()
    }

    override fun staggerFullSpan(): Boolean {
        return actualItem.staggerFullSpan()
    }

    override fun onItemAttachWindow(position: Int, view: View) {
        actualItem.onItemAttachWindow(position, view)
    }

    override fun onItemDetachWindow(position: Int, view: View) {
        actualItem.onItemDetachWindow(position, view)
    }

    override fun onItemRecycled(position: Int, view: View) {
        actualItem.onItemRecycled(position, view)
    }

//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//
//
//        other as YaksaItemWrapper<*>
//
//        if (tag != other.tag) return false
//
//        return true
//    }
}