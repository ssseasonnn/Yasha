package zlc.season.yaksa

import android.support.v7.widget.RecyclerView

const val LINEAR_LAYOUT = 0
const val GRID_LAYOUT = 1
const val STAGGERED_LAYOUT = 2

fun adapter1(): YaksaCommonStateAdapter {
    return YaksaCommonStateAdapter()
}

fun dsl1(adapter: YaksaCommonStateAdapter) :YaksaCommonStateDsl{
    return YaksaCommonStateDsl(adapter)
}

/**
 * This function is used to create a Linear list.
 *
 *@param block Item dsl
 */
fun RecyclerView.linear(enableDiff: Boolean = false,
                        block: YaksaCommonStateDsl.() -> Unit) {
    linear(::YaksaCommonStateAdapter, ::YaksaCommonStateDsl, enableDiff, block)
//    val adapter = YaksaCommonStateAdapter()
//    adapter.enableDiff = enableDiff
//    this.adapter = adapter
//    val dsl = YaksaCommonStateDsl(adapter)
//    dsl.block()
//    dsl.initLayoutManager(this, LINEAR_LAYOUT)
//    dsl.configureLayoutManager(this, adapter)
}

fun <Adapter : YaksaAdapter, Dsl : YaksaDsl> RecyclerView.linear(
        adapterFactory: () -> Adapter,
        dslFactory: (Adapter) -> Dsl,
        enableDiff: Boolean = false,
        block: Dsl.() -> Unit) {

    initDsl(this, LINEAR_LAYOUT,
            adapterFactory, dslFactory,
            enableDiff, block)
}

/**
 * This function is used to create a Grid list.
 *
 *@param block Item dsl
 */
fun RecyclerView.grid(enableDiff: Boolean = false,
                      block: YaksaCommonStateDsl.() -> Unit) {
    grid(::YaksaCommonStateAdapter, ::YaksaCommonStateDsl, enableDiff, block)
}

fun <Adapter : YaksaAdapter, Dsl : YaksaDsl> RecyclerView.grid(
        adapterFactory: () -> Adapter,
        dslFactory: (Adapter) -> Dsl,
        enableDiff: Boolean = false,
        block: Dsl.() -> Unit
) {
    initDsl(this, GRID_LAYOUT,
            adapterFactory, dslFactory,
            enableDiff, block)
}

/**
 * This function is used to create a Stagger list.
 *
 *@param block Item dsl
 */
fun RecyclerView.stagger(enableDiff: Boolean = false,
                         block: YaksaCommonStateDsl.() -> Unit) {
    stagger(::YaksaCommonStateAdapter, ::YaksaCommonStateDsl, enableDiff, block)
}

fun <Adapter : YaksaAdapter, Dsl : YaksaDsl> RecyclerView.stagger(
        adapterFactory: () -> Adapter,
        dslFactory: (Adapter) -> Dsl,
        enableDiff: Boolean = false,
        block: Dsl.() -> Unit
) {
    initDsl(this, STAGGERED_LAYOUT,
            adapterFactory, dslFactory,
            enableDiff, block)
}

private fun <Adapter : YaksaAdapter, Dsl : YaksaDsl> initDsl(
        target: RecyclerView,
        type: Int,
        adapterFactory: () -> Adapter,
        dslFactory: (Adapter) -> Dsl,
        enableDiff: Boolean = false,
        block: Dsl.() -> Unit
) {
    val adapter = adapterFactory()
    adapter.enableDiff = enableDiff
    target.adapter = adapter
    val dsl = dslFactory(adapter)
    dsl.block()
    dsl.initLayoutManager(target, type)
    dsl.configureLayoutManager(target, adapter)
}