package zlc.season.yaksa

import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.AdapterListUpdateCallback
import android.support.v7.util.DiffUtil

abstract class YaksaBaseAdapter : YaksaAbstractAdapter() {
    var enableDiff: Boolean = false

    private val data = mutableListOf<YaksaItem>()

    private val asyncListDiffer by lazy {
        AsyncListDiffer(AdapterListUpdateCallback(this),
                AsyncDifferConfig.Builder(DiffCallback()).build())
    }

    override fun submitList(list: List<YaksaItem>) {
        if (enableDiff) {
            asyncListDiffer.submitList(list)
        } else {
            this.data.clear()
            this.data.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItem(position: Int): YaksaItem {
        return if (enableDiff) {
            asyncListDiffer.currentList[position]
        } else {
            data[position]
        }
    }

    override fun getItemCount(): Int {
        return if (enableDiff) {
            asyncListDiffer.currentList.size
        } else {
            data.size
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