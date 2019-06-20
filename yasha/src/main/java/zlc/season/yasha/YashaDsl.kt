package zlc.season.yasha

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.support.v7.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.yasha_state_view_holder.*

class YashaDsl(val adapter: YashaAdapter) {
    private var orientation = VERTICAL
    private var reverse = false
    private var spanCount = 1

    init {
        renderItem<YashaStateItem> {
            res(R.layout.yasha_state_view_holder)
            onBind {
                state_view.setState(it)
            }
        }
    }

    /**
     * Set the orientation
     *
     * @param orientation   Layout orientation. VERTICAL or HORIZONTAL
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

    inline fun <reified T : YashaItem> renderItem(block: YashaItemDsl<T>.() -> Unit) {
        val dsl = YashaItemDsl<T>()
        dsl.block()
        dsl.prepare(T::class.hashCode(), adapter)
    }

    fun initLayoutManager(target: RecyclerView, type: Int) {
        target.layoutManager = when (type) {
            LINEAR_LAYOUT -> LinearLayoutManager(target.context, orientation, reverse)
            GRID_LAYOUT -> GridLayoutManager(target.context, spanCount, orientation, reverse)
            STAGGERED_LAYOUT -> StaggeredGridLayoutManager(spanCount, orientation)
            else -> throw IllegalStateException("This should never happen!")
        }
    }
}