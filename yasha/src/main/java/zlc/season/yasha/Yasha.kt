package zlc.season.yasha

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

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
    dataSource: YashaDataSource,
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
    dataSource: YashaDataSource,
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
    dataSource: YashaDataSource,
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
    dataSource: YashaDataSource,
    enableDefaultState: Boolean = false,
    shouldInvalidate: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(CUSTOM_LAYOUT, dataSource, enableDefaultState, shouldInvalidate, block, customLayoutManager)
}

/**
 * Create a pager List.
 */
fun RecyclerView.pager(
    dataSource: YashaDataSource,
    enableDefaultState: Boolean = false,
    shouldInvalidate: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(PAGER_LAYOUT, dataSource, enableDefaultState, shouldInvalidate, block)
}

/**
 * Create a vertical viewpager2
 */
fun ViewPager2.vertical(
    dataSource: YashaDataSource,
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
    dataSource: YashaDataSource,
    enableDefaultState: Boolean = false,
    shouldInvalidate: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(true, dataSource, enableDefaultState, shouldInvalidate, block)
}


private fun RecyclerView.initDsl(
    type: Int,
    dataSource: YashaDataSource,
    enableDefaultState: Boolean,
    shouldInvalidate: Boolean,
    block: YashaDsl.() -> Unit,
    customLayoutManager: RecyclerView.LayoutManager? = null
) {
    val adapter = YashaAdapter(dataSource, shouldInvalidate)

    val dsl = YashaDsl(adapter)
    dsl.block()
    dsl.init(this, type, enableDefaultState, customLayoutManager)
}

private fun ViewPager2.initDsl(
    isHorizontal: Boolean,
    dataSource: YashaDataSource,
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