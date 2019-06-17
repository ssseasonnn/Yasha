package zlc.season.yasha

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.support.v7.widget.StaggeredGridLayoutManager
import zlc.season.paging.PagingItem

open class YaksaBaseDsl(val adapter: YashaAdapter) {
    private var orientation = VERTICAL
    private var reverse = false
    private var spanCount = 1

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

    inline fun <reified T : PagingItem> renderItem(
            block: YashaItemDsl<T>.() -> Unit
    ) {
        val dsl = YashaItemDsl<T>()
        dsl.block()
        adapter.builderMap[T::class.hashCode()] = dsl::builder
    }

    open fun initLayoutManager(target: RecyclerView, type: Int) {
        target.layoutManager = when (type) {
            LINEAR_LAYOUT -> LinearLayoutManager(target.context, orientation, reverse)
            GRID_LAYOUT -> GridLayoutManager(target.context, spanCount, orientation, reverse)
            STAGGERED_LAYOUT -> StaggeredGridLayoutManager(spanCount, orientation)
            else -> throw IllegalStateException("This should never happen!")
        }
    }

    open fun configureLayoutManager(target: RecyclerView) {
//        val layoutManager = target.layoutManager
//        if (layoutManager is GridLayoutManager) {
//            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                override fun getSpanSize(position: Int): Int {
//                    return adapter.getItem(position).gridSpanSize()
//                }
//            }
//        }
    }
}