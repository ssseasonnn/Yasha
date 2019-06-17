package zlc.season.yasha

import android.support.v7.widget.RecyclerView
import zlc.season.paging.DataSource

const val LINEAR_LAYOUT = 0
const val GRID_LAYOUT = 1
const val STAGGERED_LAYOUT = 2

/**
 * Create a linear list with default Adapter and default Dsl.
 *
 * Adapter default is [zlc.season.yasha.YaksaCommonStateAdapter].
 *
 * Dsl default is [zlc.season.yasha.YaksaCommonStateDsl].
 *
 * @param enableDiff If true，yaksa will use [android.support.v7.util.DiffUtil], default is true.
 *
 * @param block Item dsl.
 */
fun RecyclerView.linear(dataSource: DataSource<YaksaItem>, block: YaksaBaseDsl.() -> Unit) {
    initDsl(this, LINEAR_LAYOUT, dataSource, block)
}


/**
 * Create a grid list with default Adapter and default Dsl.
 *
 * Adapter default is [zlc.season.yasha.YaksaCommonStateAdapter].
 *
 * Dsl default is [zlc.season.yasha.YaksaCommonStateDsl].
 *
 * @param enableDiff If true，yaksa will use [android.support.v7.util.DiffUtil], default is true.
 *
 * @param block Item dsl
 */
fun RecyclerView.grid(dataSource: DataSource<YaksaItem>, block: YaksaBaseDsl.() -> Unit) {
    initDsl(this, GRID_LAYOUT, dataSource, block)
}


/**
 * Create a stagger list with default Adapter and default Dsl.
 *
 * Adapter default is [zlc.season.yasha.YaksaCommonStateAdapter].
 *
 * Dsl default is [zlc.season.yasha.YaksaCommonStateDsl].
 *
 * @param enableDiff If true，yaksa will use [android.support.v7.util.DiffUtil], default is true.
 *
 * @param block Item dsl
 */
fun RecyclerView.stagger(dataSource: DataSource<YaksaItem>, block: YaksaBaseDsl.() -> Unit) {
    initDsl(this, STAGGERED_LAYOUT, dataSource, block)
}


private fun initDsl(
        target: RecyclerView, type: Int,
        dataSource: DataSource<YaksaItem>,
        block: YaksaBaseDsl.() -> Unit
) {
    val adapter = YashaAdapter(dataSource)
    target.adapter = adapter

    val dsl = YaksaBaseDsl(adapter)
    dsl.block()
    dsl.initLayoutManager(target, type)
    dsl.configureLayoutManager(target)
}