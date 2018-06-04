package zlc.season.yaksa

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zlc.season.yaksa.YaksaAdapter.YaksaViewHolder

open class YaksaAdapter : RecyclerView.Adapter<YaksaViewHolder>() {
    private val data: MutableList<YaksaItem> = mutableListOf()

    fun submitList(list: MutableList<out YaksaItem>) {
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
        data[position].render(holder.itemView)
    }

    private fun inflate(parent: ViewGroup, resId: Int): View {
        return LayoutInflater.from(parent.context).inflate(resId, parent, false)
    }

    class YaksaViewHolder(view: View) : RecyclerView.ViewHolder(view)
}