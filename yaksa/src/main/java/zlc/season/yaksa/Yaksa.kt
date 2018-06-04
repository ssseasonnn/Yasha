package zlc.season.yaksa

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.LayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager


const val LINEAR_LAYOUT = 0
const val GRID_LAYOUT = 1
const val STAGGERED_LAYOUT = 2

fun RecyclerView.linear(block: YaksaDsl.() -> Unit) {
    initDsl(this, LINEAR_LAYOUT, block)
}


fun RecyclerView.grid(block: YaksaDsl.() -> Unit) {
    initDsl(this, GRID_LAYOUT, block)
}

fun RecyclerView.stagger(block: YaksaDsl.() -> Unit) {
    initDsl(this, STAGGERED_LAYOUT, block)
}

private fun initDsl(target: RecyclerView, type: Int, block: YaksaDsl.() -> Unit) {
    checkAdapter(target)

    val dsl = YaksaDsl()
    dsl.block()

    initLayoutManager(target, dsl, type)

    val adapter = target.adapter as YaksaAdapter
    adapter.submitList(dsl.dataSet)
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
        val layoutManager: LayoutManager = when (type) {
            LINEAR_LAYOUT -> LinearLayoutManager(target.context, dsl.orientation, dsl.reverse)
            GRID_LAYOUT -> GridLayoutManager(target.context, dsl.spanCount, dsl.orientation, dsl.reverse)
            STAGGERED_LAYOUT -> StaggeredGridLayoutManager(dsl.spanCount, dsl.orientation)
            else -> throw IllegalStateException("This should never happen!")
        }

        target.layoutManager = layoutManager
    }
}

private fun checkNeedNew(source: LayoutManager, dsl: YaksaDsl): Boolean {
    return when (source) {
        is GridLayoutManager -> checkGrid(source, dsl)            //Grid must check before Linear
        is LinearLayoutManager -> checkLinear(source, dsl)
        is StaggeredGridLayoutManager -> checkStagger(source, dsl)
        else -> throw  IllegalStateException("This should never happen!")
    }
}

private fun checkStagger(source: StaggeredGridLayoutManager, dsl: YaksaDsl): Boolean {
    if (source.orientation == dsl.orientation &&
            source.spanCount == dsl.spanCount) {
        return false
    }
    return true
}

private fun checkLinear(source: LinearLayoutManager, dsl: YaksaDsl): Boolean {
    if (source.orientation == dsl.orientation &&
            source.reverseLayout == dsl.reverse) {
        return false
    }
    return true
}

private fun checkGrid(source: GridLayoutManager, dsl: YaksaDsl): Boolean {
    if (source.orientation == dsl.orientation &&
            source.spanCount == dsl.spanCount &&
            source.reverseLayout == dsl.reverse) {
        return false
    }
    return true
}


