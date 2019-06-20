package zlc.season.yasha

import android.view.View
import kotlinx.android.extensions.LayoutContainer
import zlc.season.sange.SangeItem
import zlc.season.sange.SangeViewHolder

interface YashaItem : SangeItem


class YashaScope(override val containerView: View) : LayoutContainer


class YashaStateItem(val state: Int, val retry: () -> Unit) : YashaItem


open class YashaViewHolder(containerView: View) : SangeViewHolder<YashaItem>(containerView)

