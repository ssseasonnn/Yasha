package zlc.season.yaksa

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.support.v7.widget.StaggeredGridLayoutManager

class YaksaDsl {
    internal var orientation = VERTICAL
    internal var reverse = false
    internal var spanCount = 1

    internal var headerList = mutableListOf<YaksaItem>()
    internal var itemList = mutableListOf<YaksaItem>()
    internal var footerList = mutableListOf<YaksaItem>()


    internal fun items(): List<YaksaItem> {
        val result = mutableListOf<YaksaItem>()
        result.addAll(headerList)
        result.addAll(itemList)
        result.addAll(footerList)
        return result
    }


    /**
     * Set the orientation, default is [android.support.v7.widget.RecyclerView.VERTICAL]
     *
     * @param orientation   Layout orientation. Should be [android.support.v7.widget.RecyclerView.VERTICAL]
     *                      or [android.support.v7.widget.RecyclerView.HORIZONTAL]
     */
    fun orientation(orientation: Int) {
        this.orientation = orientation
    }

    /**
     * Set whether to reverse the list
     *
     * @param reverse When set to true, layouts from end to start.
     */
    fun reverse(reverse: Boolean) {
        this.reverse = reverse
    }

    /**
     * Set SpanCount for Grid and Stagger
     *
     * @param spanCount spanCount
     */
    fun spanCount(spanCount: Int) {
        this.spanCount = spanCount
    }

    fun clearAll() {
        headerList.clear()
        itemList.clear()
        footerList.clear()
    }

    fun clearHeaders() {
        headerList.clear()
    }

    fun clearItems() {
        headerList.clear()
    }

    fun clearFooters() {
        footerList.clear()
    }

    fun <T> renderHeaders(dataSource: List<T>, block: (T) -> YaksaItem) {
        dataSource.forEach {
            headerList.add(YaksaItemWrapper(it, block(it)))
        }
    }

    fun <T> renderHeadersByDsl(dataSource: List<T>, block: YaksaItemDsl.(T) -> Unit) {
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            headerList.add(YaksaItemWrapper(it, dsl.internal()))
        }
    }

    fun <T> renderItems(dataSource: List<T>, block: (T) -> YaksaItem) {
        dataSource.forEach {
            itemList.add(YaksaItemWrapper(it, block(it)))
        }
    }


    fun <T> renderItemsByDsl(dataSource: List<T>, block: YaksaItemDsl.(T) -> Unit) {
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            itemList.add(YaksaItemWrapper(it, dsl.internal()))
        }
    }

    fun <T> renderFooters(dataSource: List<T>, block: (T) -> YaksaItem) {
        dataSource.forEach {
            footerList.add(YaksaItemWrapper(it, block(it)))
        }
    }

    fun <T> renderFootersByDsl(dataSource: List<T>, block: YaksaItemDsl.(T) -> Unit) {
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            footerList.add(YaksaItemWrapper(it, dsl.internal()))
        }
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