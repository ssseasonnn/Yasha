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

private fun initDsl(target: RecyclerView, type: Int,
                    block: YaksaDsl.() -> Unit) {

    val adapter = checkAdapter(target)
    val dsl = YaksaDsl(adapter)
    dsl.block()
    initLayoutManager(target, dsl, type)
    configureLayoutManager(target.layoutManager, adapter)
}

private fun checkAdapter(target: RecyclerView): YaksaCommonAdapter {
    if (target.adapter == null) {
        target.adapter = YaksaCommonAdapter()
    }

    if (target.adapter !is YaksaCommonAdapter) {
        target.adapter = YaksaCommonAdapter()
    }

    return target.adapter as YaksaCommonAdapter
}

private fun initLayoutManager(target: RecyclerView, dsl: YaksaDsl, type: Int) {
    var needNew = true
    var layoutManager = target.layoutManager
    if (layoutManager != null) {
        needNew = checkNeedNew(layoutManager, dsl, type)
    }

    if (needNew) {
        layoutManager = newLayoutManager(type, target, dsl)
        target.layoutManager = layoutManager
    }
}

private fun newLayoutManager(type: Int, target: RecyclerView, dsl: YaksaDsl): LayoutManager {
    return when (type) {
        LINEAR_LAYOUT -> LinearLayoutManager(target.context, dsl.orientation, dsl.reverse)
        GRID_LAYOUT -> GridLayoutManager(target.context, dsl.spanCount, dsl.orientation, dsl.reverse)
        STAGGERED_LAYOUT -> StaggeredGridLayoutManager(dsl.spanCount, dsl.orientation)
        else -> throw IllegalStateException("This should never happen!")
    }
}

private fun configureLayoutManager(layoutManager: LayoutManager, adapter: YaksaAdapter) {
    if (layoutManager is GridLayoutManager) {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return adapter.getYaksaItem(position).gridSpanSize()
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


