package zlc.season.yaksa

import android.support.v7.widget.RecyclerView.VERTICAL

class YaksaDsl {
    var orientation = VERTICAL
    var reverse = false
    var spanCount = 0

    val dataSet = mutableListOf<YaksaItem>()

    fun orientation(orientation: Int) {
        this.orientation = orientation
    }

    fun reverse(reverse: Boolean) {
        this.reverse = reverse
    }

    fun spanCount(span: Int) {
        this.spanCount = span
    }

    fun header(block: () -> YaksaItem) {
        dataSet.add(block())
    }

    fun headerDsl(block: YaksaItemDsl.() -> Unit) {
        val dsl = YaksaItemDsl()
        dsl.block()
        dataSet.add(dsl.internal())
    }

    fun item(block: () -> YaksaItem) {
        dataSet.add(block())
    }

    fun itemDsl(block: YaksaItemDsl.() -> Unit) {
        val dsl = YaksaItemDsl()
        dsl.block()
        dataSet.add(dsl.internal())
    }

    fun footer(block: () -> YaksaItem) {
        dataSet.add(block())
    }

    fun footerDsl(block: YaksaItemDsl.() -> Unit) {
        val dsl = YaksaItemDsl()
        dsl.block()
        dataSet.add(dsl.internal())
    }
}