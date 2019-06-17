package zlc.season.yasha

import android.view.ViewGroup
import zlc.season.paging.PagingViewHolder

abstract class YashaViewHolder(parent: ViewGroup, res: Int) : PagingViewHolder<YaksaItem>(parent, res) {
    fun onAttach() {

    }

    fun onDetach() {}

    fun onRecycled() {}
}