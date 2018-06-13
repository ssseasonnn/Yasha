package zlc.season.yaksaproject

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager


fun RecyclerView.onReachEnd(block: () -> Unit) {
    this.addOnScrollListener(OnScrollListener(block))
}

private class OnScrollListener(val block: () -> Unit) : RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        //当滚动到最后一个item时,自动加载更多
        if (isLastItem(recyclerView!!)) {
            block()
        }
    }

    private fun isLastItem(recyclerView: RecyclerView): Boolean {
        val visibleItemCount = recyclerView.layoutManager.childCount
        val totalItemCount = recyclerView.layoutManager.itemCount
        val lastVisibleItemPosition = getLastVisibleItemPosition(recyclerView.layoutManager)

        return visibleItemCount > 0 &&
                lastVisibleItemPosition >= totalItemCount - 1 &&
                totalItemCount >= visibleItemCount
    }

    private fun getLastVisibleItemPosition(layoutManager: RecyclerView.LayoutManager): Int {
        return when (layoutManager) {
            is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
            is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
            is StaggeredGridLayoutManager -> findLastVisibleItemPosition(layoutManager)
            else -> throw IllegalStateException("Unsupported layout manager")
        }
    }

    private fun findLastVisibleItemPosition(layoutManager: StaggeredGridLayoutManager): Int {
        val positions = IntArray(layoutManager.spanCount)
        layoutManager.findLastVisibleItemPositions(positions)
        return positions.max() ?: 0
    }
}