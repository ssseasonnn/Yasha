package zlc.season.yasha

import androidx.recyclerview.widget.RecyclerView
import zlc.season.sange.DataSource

const val LINEAR_LAYOUT = 0
const val GRID_LAYOUT = 1
const val STAGGERED_LAYOUT = 2

/**
 * Create a linear list .
 *
 * @param dataSource The data source.
 *
 */
fun RecyclerView.linear(
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(this, LINEAR_LAYOUT, dataSource, enableDefaultState, block)
}

/**
 * Create a grid list .
 *
 * @param dataSource The data source.
 *
 */
fun RecyclerView.grid(
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(this, GRID_LAYOUT, dataSource, enableDefaultState, block)
}

/**
 * Create a stagger list .
 *
 * @param dataSource The data source.
 *
 */
fun RecyclerView.stagger(
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean = true,
    block: YashaDsl.() -> Unit
) {
    initDsl(this, STAGGERED_LAYOUT, dataSource, enableDefaultState, block)
}

private fun initDsl(
    target: RecyclerView,
    type: Int,
    dataSource: DataSource<YashaItem>,
    enableDefaultState: Boolean,
    block: YashaDsl.() -> Unit
) {
    val adapter = YashaAdapter(dataSource)

    val dsl = YashaDsl(adapter)
    dsl.block()
    dsl.init(target, type, enableDefaultState)

    target.adapter = adapter
}