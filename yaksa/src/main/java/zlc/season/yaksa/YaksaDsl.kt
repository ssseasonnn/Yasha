package zlc.season.yaksa

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.support.v7.widget.StaggeredGridLayoutManager

class YaksaDsl(dataSet: MutableList<YaksaItem>) {
    internal var orientation = VERTICAL
    internal var reverse = false
    internal var spanCount = 1

    internal val dataSet: MutableList<YaksaItem> = mutableListOf()
    internal var dataSetChanged = false


    init {
        this.dataSet.addAll(dataSet)
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

    /**
     * Use this method to add an item to Recyclerview.
     *
     * @param index Which index to add
     *
     * @param block A block return a direct item instance
     */
    fun item(index: Int = -1, block: () -> YaksaItem) {
        if (index <= -1) {
            dataSet.add(block())
        } else {
            dataSet.add(index, block())
        }
        dataSetChanged = true
    }

    /**
     * Use this method to add an item to Recyclerview.
     *
     * @param index Which index to add
     *
     * @param block A dsl block
     *
     */
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