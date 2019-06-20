package zlc.season.yasha

import android.view.ViewGroup

class YashaItemBuilder(
        val gridSpanSize: Int,
        val staggerFullSpan: Boolean,
        val viewHolder: (ViewGroup) -> YashaViewHolder
)