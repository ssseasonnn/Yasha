package zlc.season.yasha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

class YashaItemDsl<T> {
    private var resId: Int = 0
    private var onBind: OnBindScope.(t: T) -> Unit = {}
    private var onBindPayload: OnBindScope.(t: T, payload: List<Any>) -> Unit = { _: T, _: List<Any> -> }
    private var onAttach: OnBindScope.(t: T) -> Unit = {}
    private var onDetach: OnBindScope.(t: T) -> Unit = {}
    private var onRecycled: OnBindScope.(t: T) -> Unit = {}

    private var gridSpanSize = 1
    private var staggerFullSpan = false

    /**
     * Set item res layout resource
     */
    fun res(res: Int) {
        this.resId = res
    }

    fun onBind(block: OnBindScope.(t: T) -> Unit) {
        this.onBind = block
    }

    fun onBindPayload(block: OnBindScope.(t: T, payload: List<Any>) -> Unit) {
        this.onBindPayload = block
    }

    fun onAttach(block: OnBindScope.(t: T) -> Unit) {
        this.onAttach = block
    }

    fun onDetach(block: OnBindScope.(t: T) -> Unit) {
        this.onDetach = block
    }

    fun onRecycled(block: OnBindScope.(t: T) -> Unit) {
        this.onRecycled = block
    }

    /**
     * Only work for Grid, set the span size of this item
     *
     * @param spanSize spanSize
     */
    fun gridSpanSize(spanSize: Int) {
        this.gridSpanSize = spanSize
    }

    /**
     * Only work for Stagger, set the fullSpan of this item
     *
     * @param fullSpan True or false
     */
    fun staggerFullSpan(fullSpan: Boolean) {
        this.staggerFullSpan = fullSpan
    }

    fun prepare(key: Int, adapter: YashaAdapter) {
        adapter.setItemBuilder(key, generateItemBuilder())
    }

    private fun generateItemBuilder(): YashaItemBuilder {
        return YashaItemBuilder(
                gridSpanSize,
                staggerFullSpan,
                ::builder
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun builder(viewGroup: ViewGroup): YashaViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(this.resId, viewGroup, false)

        return object : YashaViewHolder(view) {
            var onBindScope = OnBindScope(view)

            override fun onBind(t: YashaItem) {
                t as T
                onBindScope.onBind(t)
            }

            override fun onBindPayload(t: YashaItem, payload: MutableList<Any>) {
                t as T
                onBindScope.onBindPayload(t, payload)
            }

            override fun onAttach(t: YashaItem) {
                t as T
                onBindScope.onAttach(t)
            }

            override fun onDetach(t: YashaItem) {
                t as T
                onBindScope.onDetach(t)
            }

            override fun onRecycled(t: YashaItem) {
                t as T
                onBindScope.onRecycled(t)
            }
        }
    }

    class OnBindScope(override val containerView: View) : LayoutContainer
}