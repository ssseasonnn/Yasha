package zlc.season.yasha

import android.support.v7.widget.RecyclerView.NO_POSITION
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup
import zlc.season.paging.DataSource
import zlc.season.paging.MultiPagingAdapter
import zlc.season.paging.PagingViewHolder

class YashaAdapter(dataSource: DataSource<YaksaItem>) :
        MultiPagingAdapter<YaksaItem, YashaViewHolder>(dataSource) {

    val builderMap = mutableMapOf<Int, (ViewGroup) -> YashaViewHolder>()

    override fun getItemViewType(position: Int): Int {
        return getItem(position)::class.hashCode()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YashaViewHolder {
        val viewHolderBuilder = builderMap[viewType]
        if (viewHolderBuilder == null) {
            throw IllegalStateException("Not supported view type")
        } else {
            return viewHolderBuilder(parent)
        }
    }

    override fun onBindViewHolder(holder: YashaViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun onViewAttachedToWindow(holder: YashaViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
//        holder.checkPositionAndRun { position, view ->
//            getItem(position).onItemAttachWindow(position, view)
//            /**
//             * special handle stagger layout
//             */
//            specialStaggerItem(view, getItem(position))
//        }
    }

    override fun onViewDetachedFromWindow(holder: YashaViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
//        holder.checkPositionAndRun { position, view ->
//            getItem(position).onItemDetachWindow(position, view)
//        }
    }

    override fun onViewRecycled(holder: YashaViewHolder) {
        super.onViewRecycled(holder)
        holder.onRecycled()
//        holder.checkPositionAndRun { position, view ->
//            getItem(position).onItemRecycled(position, view)
//        }
    }

    private fun specialStaggerItem(view: View, item: YaksaItem) {
        val layoutParams = view.layoutParams
        if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
//            layoutParams.isFullSpan = item.staggerFullSpan()
        }
    }

    class YaksaViewHolder(containerView: View) :
            PagingViewHolder<YaksaItem>(containerView) {

        fun checkPositionAndRun(block: (position: Int, view: View) -> Unit) {
            if (this.adapterPosition != NO_POSITION) {
                block(this.adapterPosition, this.itemView)
            }
        }
    }
}