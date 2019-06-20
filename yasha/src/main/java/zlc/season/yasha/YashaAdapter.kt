package zlc.season.yasha

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ViewGroup
import zlc.season.sange.DataSource
import zlc.season.sange.SangeMultiAdapter


class YashaAdapter(dataSource: DataSource<YashaItem>) :
        SangeMultiAdapter<YashaItem, YashaViewHolder>(dataSource) {

    private val itemBuilderMap = mutableMapOf<Int, YashaItemBuilder>()

    fun setItemBuilder(key: Int, value: YashaItemBuilder) {
        itemBuilderMap[key] = value
    }

    private fun itemBuilder(position: Int): YashaItemBuilder? {
        return itemBuilderMap[getItemViewType(position)]
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)::class.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YashaViewHolder {
        val itemBuilder = itemBuilderMap[viewType]
        if (itemBuilder == null) {
            throw IllegalStateException("Not supported view type")
        } else {
            return itemBuilder.viewHolder(parent)
        }
    }

    override fun onViewAttachedToWindow(holder: YashaViewHolder) {
        super.onViewAttachedToWindow(holder)
        specialStaggeredGridLayout(holder)
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager

        specialGridLayout(layoutManager)
    }

    private fun specialGridLayout(layoutManager: RecyclerView.LayoutManager?) {
        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return itemBuilder(position)?.gridSpanSize ?: 1
                }
            }
        }
    }

    private fun specialStaggeredGridLayout(holder: YashaViewHolder) {
        val layoutParams = holder.itemView.layoutParams
        if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            val position = holder.adapterPosition
            layoutParams.isFullSpan = itemBuilder(position)?.staggerFullSpan ?: false
        }
    }
}