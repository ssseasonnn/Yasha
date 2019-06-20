package zlc.season.yasha

import android.support.v7.widget.RecyclerView
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
fun RecyclerView.linear(dataSource: DataSource<YashaItem>,
                        block: YashaDsl.() -> Unit) {
    initDsl(this, LINEAR_LAYOUT, dataSource, block)
}

/**
 * Create a grid list .
 *
 * @param dataSource The data source.
 *
 */
fun RecyclerView.grid(dataSource: DataSource<YashaItem>,
                      block: YashaDsl.() -> Unit) {
    initDsl(this, GRID_LAYOUT, dataSource, block)
}

/**
 * Create a stagger list .
 *
 * @param dataSource The data source.
 *
 */
fun RecyclerView.stagger(dataSource: DataSource<YashaItem>,
                         block: YashaDsl.() -> Unit) {
    initDsl(this, STAGGERED_LAYOUT, dataSource, block)
}

private fun initDsl(
        target: RecyclerView,
        type: Int,
        dataSource: DataSource<YashaItem>,
        block: YashaDsl.() -> Unit
) {
    val adapter = YashaAdapter(dataSource)

    val dsl = YashaDsl(adapter)
    dsl.block()
    dsl.initLayoutManager(target, type)

    target.adapter = adapter
}