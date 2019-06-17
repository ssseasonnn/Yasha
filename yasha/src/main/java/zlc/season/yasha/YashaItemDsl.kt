package zlc.season.yasha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

class YashaItemDsl<T> {
    private var resId: Int = 0
    private var onBind: OnBindScope.(t: T) -> Unit = {}

    /**
     * Set item res layout resource
     */
    fun res(res: Int) {
        this.resId = res
    }

    fun onBind(block: OnBindScope.(t: T) -> Unit) {
        this.onBind = block
    }

    fun builder(viewGroup: ViewGroup): YashaViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(this.resId, viewGroup, false)
        return object : YashaViewHolder(view) {
            override fun onBind(t: YaksaItem) {
                t as T
                val onBindScopeDsl = OnBindScope(view)
                onBindScopeDsl.onBind(t)
            }
        }

    }


    class OnBindScope(override val containerView: View) : LayoutContainer
}