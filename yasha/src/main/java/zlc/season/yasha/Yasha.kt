package zlc.season.yasha

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import zlc.season.sange.DataSource

const val LINEAR_LAYOUT = 0
const val GRID_LAYOUT = 1
const val STAGGERED_LAYOUT = 2
const val CUSTOM_LAYOUT = 3
const val PAGER_LAYOUT = 4

/**
 * Create a linear list .
 *
 * @param dataSource The data source.
 *
 */
fun RecyclerView.linear(
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean = false,
    shouldInvalidate: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(LINEAR_LAYOUT, dataSource, enableDefaultState, shouldInvalidate, block)
}

/**
 * Create a grid list .
 *
 * @param dataSource The data source.
 *
 */
fun RecyclerView.grid(
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean = false,
    shouldInvalidate: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(GRID_LAYOUT, dataSource, enableDefaultState, shouldInvalidate, block)
}

/**
 * Create a stagger list .
 *
 * @param dataSource The data source.
 *
 */
fun RecyclerView.stagger(
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean = false,
    shouldInvalidate: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(STAGGERED_LAYOUT, dataSource, enableDefaultState, shouldInvalidate, block)
}

/**
 * Create a custom list .
 *
 * @param dataSource The data source.
 *
 */
fun RecyclerView.custom(
    customLayoutManager: RecyclerView.LayoutManager,
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean = false,
    shouldInvalidate: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(CUSTOM_LAYOUT, dataSource, enableDefaultState, shouldInvalidate, block, customLayoutManager)
}

fun RecyclerView.pager(
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean = false,
    shouldInvalidate: Boolean = true,
    onPageChanged: ((position: Int, item: YashaItem, itemView: View) -> Unit)? = null,
    block: YashaDsl.() -> Unit
) {
    initDsl(PAGER_LAYOUT, dataSource, enableDefaultState, shouldInvalidate, block, onPageChanged = onPageChanged)
}

/**
 * Create a vertical viewpager2
 */
fun ViewPager2.vertical(
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean = false,
    shouldInvalidate: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(false, dataSource, enableDefaultState, shouldInvalidate, block)
}

/**
 * Create a horizontal viewpager2
 */
fun ViewPager2.horizontal(
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean = false,
    shouldInvalidate: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(true, dataSource, enableDefaultState, shouldInvalidate, block)
}


private fun RecyclerView.initDsl(
    type: Int,
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean,
    shouldInvalidate: Boolean,
    block: YashaDsl.() -> Unit,
    customLayoutManager: RecyclerView.LayoutManager? = null,
    onPageChanged: ((Int, YashaItem, View) -> Unit)? = null
) {
    val adapter = YashaAdapter(dataSource, shouldInvalidate)

    val dsl = YashaDsl(adapter)
    dsl.block()
    dsl.init(this, type, enableDefaultState, customLayoutManager)

    this.adapter = adapter

    if (onPageChanged != null) {
        this.addOnScrollListener(YashaPagerListener(this, onPageChanged))
    }
}

private fun ViewPager2.initDsl(
    isHorizontal: Boolean,
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean,
    shouldInvalidate: Boolean,
    block: YashaDsl.() -> Unit
) {
    val adapter = YashaAdapter(dataSource, shouldInvalidate)

    val dsl = YashaDsl(adapter)
    dsl.block()
    dsl.initViewPager2(enableDefaultState)

    this.orientation = if (isHorizontal) ViewPager2.ORIENTATION_HORIZONTAL else ViewPager2.ORIENTATION_VERTICAL
    this.adapter = adapter
}