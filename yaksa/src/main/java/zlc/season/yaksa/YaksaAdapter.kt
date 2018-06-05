package zlc.season.yaksa

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zlc.season.yaksa.YaksaAdapter.YaksaViewHolder

class YaksaAdapter : RecyclerView.Adapter<YaksaViewHolder>() {
    internal val data: MutableList<YaksaItem> = mutableListOf()

    fun submitList(list: List<YaksaItem>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].xml()
    }

    override fun onCreateViewHolder(parent: ViewGroup, resId: Int): YaksaViewHolder {
        return YaksaViewHolder(inflate(parent, resId))
    }

    override fun onBindViewHolder(holder: YaksaViewHolder, position: Int) {
        data[position].render(position, holder.itemView)
    }

    override fun onViewAttachedToWindow(holder: YaksaViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position = holder.adapterPosition

        val lp = holder.itemView.layoutParams
        if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
            lp.isFullSpan = data[position].staggerFullSpan()
        }
    }

    private fun inflate(parent: ViewGroup, resId: Int): View {
        return LayoutInflater.from(parent.context).inflate(resId, parent, false)
    }

    class YaksaViewHolder(view: View) : RecyclerView.ViewHolder(view)
}