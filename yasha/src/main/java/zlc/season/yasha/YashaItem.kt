package zlc.season.yasha

import android.view.ViewGroup
import zlc.season.sange.SangeItem

interface TypeConflictStrategy {
    fun typeConflict(): String? = null
}

interface YashaItem : SangeItem, TypeConflictStrategy

class YashaStateItem(val state: Int, val retry: () -> Unit) : YashaItem

class YashaItemBuilder(
        val gridSpanSize: Int,
        val staggerFullSpan: Boolean,
        val viewHolder: (ViewGroup) -> YashaViewHolder
)