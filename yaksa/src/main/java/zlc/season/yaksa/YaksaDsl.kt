package zlc.season.yaksa

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.support.v7.widget.StaggeredGridLayoutManager

class YaksaDsl {
    internal var orientation = VERTICAL
    internal var reverse = false
    internal var spanCount = 0

    internal val dataSet = mutableListOf<YaksaItem>()

    fun orientation(orientation: Int) {
        this.orientation = orientation
    }

    fun reverse(reverse: Boolean) {
        this.reverse = reverse
    }

    fun spanCount(span: Int) {
        this.spanCount = span
    }

    fun item(block: () -> YaksaItem) {
        dataSet.add(block())
    }

    fun itemDsl(block: YaksaItemDsl.() -> Unit) {
        val dsl = YaksaItemDsl()
        dsl.block()
        dataSet.add(dsl.internal())
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