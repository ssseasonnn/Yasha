package zlc.season.yaksa

import android.support.v7.widget.RecyclerView

const val LINEAR_LAYOUT = 0
const val GRID_LAYOUT = 1
const val STAGGERED_LAYOUT = 2

/**
 * Create a linear list with default Adapter and default Dsl.
 *
 * Adapter default is [zlc.season.yaksa.YaksaCommonStateAdapter].
 *
 * Dsl default is [zlc.season.yaksa.YaksaCommonStateDsl].
 *
 * @param enableDiff If true，yaksa will use [android.support.v7.util.DiffUtil], default is true.
 *
 * @param block Item dsl.
 */
fun RecyclerView.linear(enableDiff: Boolean = true,
                        block: YaksaCommonStateDsl.() -> Unit) {
    linear(::YaksaCommonStateAdapter, ::YaksaCommonStateDsl, enableDiff, block)
}

/**
 * Create a linear list with default Adapter and default Dsl.
 *
 * @param adapterFactory A method to create a custom Adapter.
 *
 * @param dslFactory  A method to create a custom Dsl.
 *
 * @param enableDiff If true，yaksa will use [android.support.v7.util.DiffUtil], default is true.
 *
 * @param block Item dsl.
 */
fun <Adapter : YaksaBaseAdapter, Dsl : YaksaBaseDsl> RecyclerView.linear(
        adapterFactory: () -> Adapter,
        dslFactory: (Adapter) -> Dsl,
        enableDiff: Boolean = true,
        block: Dsl.() -> Unit) {

    initDsl(this, LINEAR_LAYOUT,
            adapterFactory, dslFactory,
            enableDiff, block)
}

/**
 * Create a grid list with default Adapter and default Dsl.
 *
 * Adapter default is [zlc.season.yaksa.YaksaCommonStateAdapter].
 *
 * Dsl default is [zlc.season.yaksa.YaksaCommonStateDsl].
 *
 * @param enableDiff If true，yaksa will use [android.support.v7.util.DiffUtil], default is true.
 *
 * @param block Item dsl
 */
fun RecyclerView.grid(enableDiff: Boolean = true,
                      block: YaksaCommonStateDsl.() -> Unit) {
    grid(::YaksaCommonStateAdapter, ::YaksaCommonStateDsl, enableDiff, block)
}

/**
 * Create a grid list with default Adapter and default Dsl.
 *
 * @param adapterFactory A method to create a custom Adapter.
 *
 * @param dslFactory  A method to create a custom Dsl.
 *
 * @param enableDiff If true，yaksa will use [android.support.v7.util.DiffUtil], default is true.
 *
 * @param block Item dsl.
 */
fun <Adapter : YaksaBaseAdapter, Dsl : YaksaBaseDsl> RecyclerView.grid(
        adapterFactory: () -> Adapter,
        dslFactory: (Adapter) -> Dsl,
        enableDiff: Boolean = true,
        block: Dsl.() -> Unit
) {
    initDsl(this, GRID_LAYOUT,
            adapterFactory, dslFactory,
            enableDiff, block)
}

/**
 * Create a stagger list with default Adapter and default Dsl.
 *
 * Adapter default is [zlc.season.yaksa.YaksaCommonStateAdapter].
 *
 * Dsl default is [zlc.season.yaksa.YaksaCommonStateDsl].
 *
 * @param enableDiff If true，yaksa will use [android.support.v7.util.DiffUtil], default is true.
 *
 * @param block Item dsl
 */
fun RecyclerView.stagger(enableDiff: Boolean = true,
                         block: YaksaCommonStateDsl.() -> Unit) {
    stagger(::YaksaCommonStateAdapter, ::YaksaCommonStateDsl, enableDiff, block)
}

/**
 * Create a stagger list with default Adapter and default Dsl.
 *
 * @param adapterFactory A method to create a custom Adapter.
 *
 * @param dslFactory  A method to create a custom Dsl.
 *
 * @param enableDiff If true，yaksa will use [android.support.v7.util.DiffUtil], default is true.
 *
 * @param block Item dsl.
 */
fun <Adapter : YaksaBaseAdapter, Dsl : YaksaBaseDsl> RecyclerView.stagger(
        adapterFactory: () -> Adapter,
        dslFactory: (Adapter) -> Dsl,
        enableDiff: Boolean = true,
        block: Dsl.() -> Unit
) {
    initDsl(this, STAGGERED_LAYOUT,
            adapterFactory, dslFactory,
            enableDiff, block)
}

private fun <Adapter : YaksaBaseAdapter, Dsl : YaksaBaseDsl> initDsl(
        target: RecyclerView, type: Int,
        adapterFactory: () -> Adapter,
        dslFactory: (Adapter) -> Dsl,
        enableDiff: Boolean = true,
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