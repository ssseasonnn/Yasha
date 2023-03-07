package zlc.season.yasha

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding
import zlc.season.yasha.databinding.YashaStateViewHolderBinding

class YashaDsl(val adapter: YashaAdapter) {
    private var orientation = VERTICAL
    private var reverse = false
    private var spanCount = 1

    private var onPageChanged: ((Int, YashaItem, View) -> Unit)? = null

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

    /**
     * Set page changed callback for Pager
     */
    fun onPageChanged(block: (Int, YashaItem, View) -> Unit) {
        this.onPageChanged = block
    }

    inline fun <reified T : YashaItem> renderItem(typeConflict: String? = null, block: YashaItemDsl<T>.() -> Unit) {
        val dsl = YashaItemDsl<T>()
        dsl.block()
        dsl.prepare(type<T>(typeConflict), adapter)
    }

    inline fun <reified T : YashaItem, reified VB : ViewBinding> renderBindingItem(
        typeConflict: String? = null,
        block: YashaBindingItemDsl<T, VB>.() -> Unit
    ) {
        val dsl = YashaBindingItemDsl<T, VB>(VB::class.java.getInflateMethod())
        dsl.block()
        dsl.prepare(type<T>(typeConflict), adapter)
    }

    fun init(
        target: RecyclerView,
        type: Int,
        enableDefaultState: Boolean,
        customLayoutManager: RecyclerView.LayoutManager?
    ) {
        target.layoutManager = when (type) {
            LINEAR_LAYOUT -> LinearLayoutManager(target.context, orientation, reverse)
            GRID_LAYOUT -> GridLayoutManager(target.context, spanCount, orientation, reverse)
            STAGGERED_LAYOUT -> StaggeredGridLayoutManager(spanCount, orientation)
            CUSTOM_LAYOUT -> customLayoutManager
            PAGER_LAYOUT -> LinearLayoutManager(target.context, orientation, reverse)
            else -> throw IllegalStateException("This should never happen!")
        }

        if (enableDefaultState) {
            setDefaultStateItem()
        }

        target.adapter = adapter

        onPageChanged?.let {
            target.addOnScrollListener(YashaPagerListener(target, it))
        }
    }

    fun initViewPager2(enableDefaultState: Boolean) {
        if (enableDefaultState) {
            setDefaultStateItem()
        }
    }

    private fun setDefaultStateItem() {
        renderBindingItem<YashaStateItem, YashaStateViewHolderBinding> {
            viewBinding(YashaStateViewHolderBinding::inflate)
            gridSpanSize(spanCount)
            staggerFullSpan(true)
            onBind {
                itemBinding.stateView.setState(data)
            }
        }
    }
}