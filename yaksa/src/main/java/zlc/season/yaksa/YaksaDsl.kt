package zlc.season.yaksa

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.support.v7.widget.StaggeredGridLayoutManager

class YaksaDsl(dataSet: MutableList<YaksaItem>) {
    internal var orientation = VERTICAL
    internal var reverse = false
    internal var spanCount = 0

    internal val dataSet: MutableList<YaksaItem> = mutableListOf()
    internal var dataSetChanged = false


    init {
        this.dataSet.addAll(dataSet)
    }

    fun orientation(orientation: Int) {
        this.orientation = orientation
    }

    fun reverse(reverse: Boolean) {
        this.reverse = reverse
    }

    fun spanCount(span: Int) {
        this.spanCount = span
    }

    fun item(index: Int = -1, block: () -> YaksaItem) {
        if (index <= -1) {
            dataSet.add(block())
        } else {
            dataSet.add(index, block())
        }
        dataSetChanged = true
    }

    fun itemDsl(index: Int = -1, block: YaksaItemDsl.() -> Unit) {
        val dsl = YaksaItemDsl()
        dsl.block()
        if (index <= -1) {
            dataSet.add(dsl.internal())
        } else {
            dataSet.add(index, dsl.internal())
        }
        dataSetChanged = true
    }

    internal fun checkStagger(source: StaggeredGridLayoutManager): Boolean {
        if (source.orientation == orientation &&
                source.spanCount == spanCount) {
            return false
        }
        return true
    }

    internal fun checkLinear(source: LinearLayoutManager): Boolean {
        if (source.orientation == orientation &&
                source.reverseLayout == reverse) {
            return false
        }
        return true
    }

    internal fun checkGrid(source: GridLayoutManager): Boolean {
        if (source.orientation == orientation &&
                source.spanCount == spanCount &&
                source.reverseLayout == reverse) {
            return false
        }
        return true
    }
}