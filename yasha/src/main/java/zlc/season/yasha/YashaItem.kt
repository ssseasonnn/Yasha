package zlc.season.yasha

import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import zlc.season.sange.SangeItem
import zlc.season.sange.SangeViewHolder

/**
 * Item interface
 */
interface YashaItem : SangeItem


open class YashaViewHolder(containerView: View) :
        SangeViewHolder<YashaItem>(containerView)

class YashaViewHolderScope(override val containerView: View) : LayoutContainer

class YashaItemBuilder(
        val gridSpanSize: Int,
        val staggerFullSpan: Boolean,
        val viewHolder: (ViewGroup) -> YashaViewHolder
)
