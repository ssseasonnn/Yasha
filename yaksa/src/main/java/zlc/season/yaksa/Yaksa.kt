package zlc.season.yaksa

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

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

private fun initDsl(target: RecyclerView, type: Int, block: YaksaDsl.() -> Unit) {
    checkAdapter(target)

    val adapter = target.adapter as YaksaAdapter

    val dsl = YaksaDsl()
    adapter.pop(dsl)

    dsl.block()

    initLayoutManager(target, dsl, type)

    adapter.stash(dsl)
    adapter.submitList(dsl.items())
}

private fun checkAdapter(target: RecyclerView) {
    if (target.adapter == null) {
        target.adapter = YaksaAdapter()
    }

    if (target.adapter !is YaksaAdapter) {
        throw IllegalStateException("Adapter must be YaksaAdapter")
    }
}

private fun initLayoutManager(target: RecyclerView, dsl: YaksaDsl, type: Int) {
    var needNew = true
    val source = target.layoutManager
    if (source != null) {
        needNew = checkNeedNew(source, dsl)
    }

    if (needNew) {
        val layoutManager = newLayoutManager(type, target, dsl)
        configureLayoutManager(layoutManager, dsl)
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

private fun configureLayoutManager(layoutManager: LayoutManager, dsl: YaksaDsl) {
    if (layoutManager is GridLayoutManager) {
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return dsl.items()[position].gridSpanSize()
            }
        }
    }
}

private fun checkNeedNew(source: LayoutManager, dsl: YaksaDsl): Boolean {
    return when (source) {
        is GridLayoutManager -> dsl.checkGrid(source)            //Grid must check before Linear
        is LinearLayoutManager -> dsl.checkLinear(source)
        is StaggeredGridLayoutManager -> dsl.checkStagger(source)
        else -> throw  IllegalStateException("This should never happen!")
    }
}


