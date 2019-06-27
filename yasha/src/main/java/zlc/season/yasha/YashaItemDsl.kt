package zlc.season.yasha

import android.view.LayoutInflater
import android.view.ViewGroup

class YashaItemDsl<T : YashaItem> {
    private var resId: Int = 0
    private var onBind: YashaScope<T>.() -> Unit = {}
    private var onBindPayload: YashaScope<T>.(payload: List<Any>) -> Unit = { _: List<Any> -> }

    private var onAttach: YashaScope<T>.() -> Unit = {}
    private var onDetach: YashaScope<T>.() -> Unit = {}
    private var onRecycled: YashaScope<T>.() -> Unit = {}

    private var gridSpanSize = 1
    private var staggerFullSpan = false

    /**
     * Set item res layout resource
     */
    fun res(res: Int) {
        this.resId = res
    }

    fun onBind(block: YashaScope<T>.() -> Unit) {
        this.onBind = block
    }

    fun onBindPayload(block: YashaScope<T>.(payload: List<Any>) -> Unit) {
        this.onBindPayload = block
    }

    fun onAttach(block: YashaScope<T>.() -> Unit) {
        this.onAttach = block
    }

    fun onDetach(block: YashaScope<T>.() -> Unit) {
        this.onDetach = block
    }

    fun onRecycled(block: YashaScope<T>.() -> Unit) {
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
            var viewHolderScope = YashaScope<T>(view)

            override fun onBind(t: YashaItem) {
                t as T
                viewHolderScope.data = t
                viewHolderScope.onBind()
            }

            override fun onBindPayload(t: YashaItem, payload: MutableList<Any>) {
                t as T
                viewHolderScope.data = t
                viewHolderScope.onBindPayload(payload)
            }

            override fun onAttach(t: YashaItem) {
                t as T
                viewHolderScope.data = t
                viewHolderScope.onAttach()
            }

            override fun onDetach(t: YashaItem) {
                t as T
                viewHolderScope.data = t
                viewHolderScope.onDetach()
            }

            override fun onRecycled(t: YashaItem) {
                t as T
                viewHolderScope.data = t
                viewHolderScope.onRecycled()
            }
        }
    }
}