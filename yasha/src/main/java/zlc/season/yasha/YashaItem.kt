package zlc.season.yasha

import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import zlc.season.sange.SangeItem
import zlc.season.sange.SangeViewHolder

interface TypeConflictStrategy {
    fun typeConflict(): String? = null
}

interface YashaItem : SangeItem, TypeConflictStrategy

class YashaScope<T : YashaItem>(override val containerView: View) : LayoutContainer {
    lateinit var data: T

    val map = mutableMapOf<String, Any>()

    fun set(key: String, value: Any) {
        map[key] = value
    }

    inline fun <reified R> get(key: String): R? {
        val find = map[key] ?: return null
        if (find is R) {
            return find
        } else {
            throw IllegalStateException("Type not match!")
        }
    }
}


class YashaStateItem(val state: Int, val retry: () -> Unit) : YashaItem


open class YashaViewHolder(containerView: View) : SangeViewHolder<YashaItem>(containerView)


class YashaItemBuilder(
    val gridSpanSize: Int,
    val staggerFullSpan: Boolean,
    val viewHolder: (ViewGroup) -> YashaViewHolder
)