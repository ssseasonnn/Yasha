package zlc.season.yasha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

class YashaItemDsl<T> : LayoutContainer {
    private var rootView: View? = null
    override var containerView: View? = rootView

    var resId: Int = 0

    var onBind: (t: T) -> Unit = {}
    var onBindWithView: (view: View, t: T) -> Unit = { view: View, t: T -> }

    /**
     * Set item res layout resource
     */
    fun res(res: Int) {
        this.resId = res
    }

    fun onBind(block: (t: T) -> Unit) {
        this.onBind = block
    }

    fun onBindWithView(block: (view: View, t: T) -> Unit) {
        this.onBindWithView = block
    }

    fun internalItem(viewGroup: ViewGroup): YashaViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(this.resId, viewGroup, false)
        rootView = view
        containerView = view

        return object : YashaViewHolder(view) {
            override fun onBind(t: YaksaItem) {
                t as T
                this@YashaItemDsl.onBind(t)
                this@YashaItemDsl.onBindWithView(view, t)
            }
        }
    }
}