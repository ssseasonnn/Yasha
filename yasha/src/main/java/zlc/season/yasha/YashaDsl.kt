package zlc.season.yasha

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.yasha_state_view_holder.*

class YashaDsl(val adapter: YashaAdapter) {
    private var orientation = VERTICAL
    private var reverse = false
    private var spanCount = 1

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

    inline fun <reified T : YashaItem> renderItem(typeConflict: String? = null, block: YashaItemDsl<T>.() -> Unit) {
        val dsl = YashaItemDsl<T>()
        dsl.block()
        dsl.prepare(type<T>(typeConflict), adapter)
    }

    fun init(target: RecyclerView, type: Int, enableDefaultState: Boolean) {

        target.layoutManager = when (type) {
            LINEAR_LAYOUT -> LinearLayoutManager(target.context, orientation, reverse)
            GRID_LAYOUT -> GridLayoutManager(target.context, spanCount, orientation, reverse)
            STAGGERED_LAYOUT -> StaggeredGridLayoutManager(spanCount, orientation)
            else -> throw IllegalStateException("This should never happen!")
        }

        if (enableDefaultState) {
            setDefaultStateItem()
        }
    }

    private fun setDefaultStateItem() {
        renderItem<YashaStateItem> {
            res(R.layout.yasha_state_view_holder)

            onBind {
                state_view.setState(data)
            }

            gridSpanSize(spanCount)

            staggerFullSpan(true)
        }
    }
}