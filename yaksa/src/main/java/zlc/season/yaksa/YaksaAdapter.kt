package zlc.season.yaksa

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_POSITION
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zlc.season.yaksa.YaksaAdapter.YaksaViewHolder

class YaksaAdapter : ListAdapter<YaksaItem, YaksaViewHolder>(DiffCallback()) {
    private var headerList = mutableListOf<YaksaItem>()
    private var itemList = mutableListOf<YaksaItem>()
    private var footerList = mutableListOf<YaksaItem>()
    private var extraList = mutableListOf<YaksaItem>()

    override fun getItemViewType(position: Int): Int {
        return getItem(position).xml()
    }

    override fun onCreateViewHolder(parent: ViewGroup, resId: Int): YaksaViewHolder {
        return YaksaViewHolder(inflate(parent, resId))
    }

    override fun onBindViewHolder(holder: YaksaViewHolder, position: Int) {
        getItem(position).render(position, holder.itemView)
    }

    override fun onViewAttachedToWindow(holder: YaksaViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.checkPositionAndRun { position, view ->
            getItem(position).onItemAttachWindow(position, view)
            /**
             * special handle stagger layout
             */
            specialStaggerItem(view, getItem(position))
        }
    }

    override fun onViewDetachedFromWindow(holder: YaksaViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.checkPositionAndRun { position, view ->
            getItem(position).onItemDetachWindow(position, view)
        }
    }

    override fun onViewRecycled(holder: YaksaViewHolder) {
        super.onViewRecycled(holder)
        holder.checkPositionAndRun { position, view ->
            getItem(position).onItemRecycled(position, view)
        }
    }

    private fun specialStaggerItem(view: View, item: YaksaItem) {
        val layoutParams = view.layoutParams
        if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            layoutParams.isFullSpan = item.staggerFullSpan()
        }
    }

    private fun inflate(parent: ViewGroup, resId: Int): View {
        return LayoutInflater.from(parent.context).inflate(resId, parent, false)
    }

    internal fun stash(dsl: YaksaDsl) {
        this.headerList = dsl.headerList
        this.itemList = dsl.itemList
        this.footerList = dsl.footerList
        this.extraList = dsl.extraList
    }

    internal fun pop(dsl: YaksaDsl) {
        dsl.headerList = this.headerList
        dsl.itemList = this.itemList
        dsl.footerList = this.footerList
        dsl.extraList = this.extraList
    }

    class YaksaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun checkPositionAndRun(block: (position: Int, view: View) -> Unit) {
            if (this.adapterPosition != NO_POSITION) {
                block(this.adapterPosition, this.itemView)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<YaksaItem>() {
        override fun areItemsTheSame(oldItem: YaksaItem, newItem: YaksaItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: YaksaItem, newItem: YaksaItem): Boolean {
            return oldItem == newItem
        }
    }
}