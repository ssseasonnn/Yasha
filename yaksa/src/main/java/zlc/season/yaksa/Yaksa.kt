package zlc.season.yaksa

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager

const val LINEAR_LAYOUT = 0
const val GRID_LAYOUT = 1
const val STAGGERED_LAYOUT = 2

/**
 * This function is used to create a Linear list.
 *
 *@param block Item dsl
 */
fun RecyclerView.linear(block: YaksaDsl.() -> Unit) {
    initDsl(this, LINEAR_LAYOUT, block)
}

/**
 * This function is used to create a Grid list.
 *
 *@param block Item dsl
 */
fun RecyclerView.grid(block: YaksaDsl.() -> Unit) {
    initDsl(this, GRID_LAYOUT, block)
}

/**
 * This function is used to create a Stagger list.
 *
 *@param block Item dsl
 */
fun RecyclerView.stagger(block: YaksaDsl.() -> Unit) {
    initDsl(this, STAGGERED_LAYOUT, block)
}

/**
 * Create a Linear list with placeholder item
 */
fun RecyclerView.linearWithPlaceholder(block: YaksaDsl.() -> Unit) {
    initPlaceholderDsl(this, LINEAR_LAYOUT, block)
}

/**
 * Create a Grid list with placeholder item
 */
fun RecyclerView.gridWithPlaceholder(block: YaksaDsl.() -> Unit) {
    initPlaceholderDsl(this, GRID_LAYOUT, block)
}

/**
 * Create a Stagger list with placeholder item
 */
fun RecyclerView.staggerWithPlaceholder(block: YaksaDsl.() -> Unit) {
    initPlaceholderDsl(this, STAGGERED_LAYOUT, block)
}

private fun initDsl(target: RecyclerView, type: Int, block: YaksaDsl.() -> Unit) {
    val adapter = checkAdapter(target)

    val dsl = YaksaDsl()
    adapter.pop(dsl)

    dsl.block()

    initLayoutManager(target, dsl, type, false)

    adapter.stash(dsl)
    adapter.submitList(dsl.items())
}

private fun initPlaceholderDsl(target: RecyclerView, type: Int, block: YaksaDsl.() -> Unit) {
    val adapter = checkPlaceholderAdapter(target)
    val dsl = YaksaDsl()
    dsl.block()
    initLayoutManager(target, dsl, type, true)
    adapter.submitList(dsl.itemsWithPlaceholder())
}

private fun checkAdapter(target: RecyclerView): YaksaRealAdapter {
    if (target.adapter == null) {
        target.adapter = YaksaRealAdapter()
    }

    if (target.adapter !is YaksaRealAdapter) {
        target.adapter = YaksaRealAdapter()
    }

    return target.adapter as YaksaRealAdapter
}

private fun checkPlaceholderAdapter(target: RecyclerView): YaksaPlaceholderAdapter {
    if (target.adapter == null) {
        target.adapter = YaksaPlaceholderAdapter()
    }

    if (target.adapter !is YaksaPlaceholderAdapter) {
        target.adapter = YaksaPlaceholderAdapter()
    }

    return target.adapter as YaksaPlaceholderAdapter
}

private fun initLayoutManager(target: RecyclerView, dsl: YaksaDsl, type: Int, withPlaceholder: Boolean) {
    var needNew = true
    var layoutManager = target.layoutManager
    if (layoutManager != null) {
        needNew = checkNeedNew(layoutManager, dsl, type)
    }

    if (needNew) {
        layoutManager = newLayoutManager(type, target, dsl)
        target.layoutManager = layoutManager
    }

    configureLayoutManager(layoutManager, dsl, withPlaceholder)
}

private fun newLayoutManager(type: Int, target: RecyclerView, dsl: YaksaDsl): LayoutManager {
    return when (type) {
        LINEAR_LAYOUT -> LinearLayoutManager(target.context, dsl.orientation, dsl.reverse)
        GRID_LAYOUT -> GridLayoutManager(target.context, dsl.spanCount, dsl.orientation, dsl.reverse)
        STAGGERED_LAYOUT -> StaggeredGridLayoutManager(dsl.spanCount, dsl.orientation)
        else -> throw IllegalStateException("This should never happen!")
    }
}

private fun configureLayoutManager(layoutManager: LayoutManager, dsl: YaksaDsl, withPlaceholder: Boolean) {
    if (layoutManager is GridLayoutManager) {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (withPlaceholder) {
                    dsl.itemsWithPlaceholder()[position].gridSpanSize()
                } else {
                    dsl.items()[position].gridSpanSize()
                }
            }
        }
    }
}

private fun checkNeedNew(layoutManager: LayoutManager, dsl: YaksaDsl, type: Int): Boolean {
    return when (layoutManager) {
        is GridLayoutManager -> type != GRID_LAYOUT || dsl.checkGrid(layoutManager)            //Grid must check before Linear
        is LinearLayoutManager -> type != LINEAR_LAYOUT || dsl.checkLinear(layoutManager)
        is StaggeredGridLayoutManager -> type != STAGGERED_LAYOUT || dsl.checkStagger(layoutManager)
        else -> throw  IllegalStateException("This should never happen!")
    }
}


