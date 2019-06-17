package zlc.season.yasha

import android.view.View
import android.view.ViewGroup
import zlc.season.paging.PagingViewHolder

abstract class YashaViewHolder(containerView: View) : PagingViewHolder<YaksaItem>(containerView) {
    fun onAttach() {

    }

    fun onDetach() {}

    fun onRecycled() {}
}