package zlc.season.yasha

import android.view.View
import android.view.ViewGroup
import zlc.season.sange.PagingItem
import zlc.season.sange.PagingViewHolder

/**
 * Item interface
 */
interface YashaItem : PagingItem


open class YashaViewHolder(containerView: View) :
        PagingViewHolder<YashaItem>(containerView)


class YashaItemBuilder(
        val gridSpanSize: Int,
        val staggerFullSpan: Boolean,
        val viewHolder: (ViewGroup) -> YashaViewHolder
)
