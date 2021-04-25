package zlc.season.yasha

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.ViewGroup
import zlc.season.sange.DataSource
import zlc.season.sange.SangeMultiAdapter

class YashaAdapter(dataSource: DataSource<YashaItem>, shouldInvalidate: Boolean) :
        SangeMultiAdapter<YashaItem, YashaViewHolder>(dataSource, shouldInvalidate) {

    private val itemBuilderMap = mutableMapOf<Int, YashaItemBuilder>()

    fun registerItemBuilder(type: Int, value: YashaItemBuilder) {
        itemBuilderMap[type] = value
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YashaViewHolder {
        val itemBuilder = itemBuilderMap[viewType]
        if (itemBuilder == null) {
            throw IllegalStateException("Not supported view type: [$viewType]!")
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
                    return getItemBuilder(position)?.gridSpanSize ?: 1
                }
            }
        }
    }

    private fun specialStaggeredGridLayout(holder: YashaViewHolder) {
        val layoutParams = holder.itemView.layoutParams
        if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            val position = holder.adapterPosition
            layoutParams.isFullSpan = getItemBuilder(position)?.staggerFullSpan ?: false
        }
    }

    private fun getItemBuilder(position: Int): YashaItemBuilder? {
        return itemBuilderMap[getItemViewType(position)]
    }
}