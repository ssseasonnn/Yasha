package zlc.season.yasha

import android.view.LayoutInflater
import android.view.ViewGroup

class YashaItemDsl<T : YashaItem> {
    private var resId: Int = 0
    private var gridSpanSize = 1
    private var staggerFullSpan = false

    internal var initItemScope: YashaItemScope<T>.() -> Unit = {}

    internal var onBind: YashaItemScope<T>.() -> Unit = {}
    internal var onBindPayload: YashaItemScope<T>.(payload: List<Any>) -> Unit = { _: List<Any> -> }

    internal var onAttach: YashaItemScope<T>.() -> Unit = {}
    internal var onDetach: YashaItemScope<T>.() -> Unit = {}
    internal var onRecycled: YashaItemScope<T>.() -> Unit = {}

    fun initScope(block: YashaItemScope<T>.() -> Unit) {
        this.initItemScope = block
    }

    /**
     * Set item res layout resource
     */
    fun res(res: Int) {
        this.resId = res
    }

    fun onBind(block: YashaItemScope<T>.() -> Unit) {
        this.onBind = block
    }

    fun onBindPayload(block: YashaItemScope<T>.(payload: List<Any>) -> Unit) {
        this.onBindPayload = block
    }

    fun onAttach(block: YashaItemScope<T>.() -> Unit) {
        this.onAttach = block
    }

    fun onDetach(block: YashaItemScope<T>.() -> Unit) {
        this.onDetach = block
    }

    fun onRecycled(block: YashaItemScope<T>.() -> Unit) {
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

    fun prepare(type: Int, adapter: YashaAdapter) {
        adapter.registerItemBuilder(
                type, YashaItemBuilder(
                gridSpanSize,
                staggerFullSpan,
                ::builder)
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun builder(viewGroup: ViewGroup): YashaViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(this.resId, viewGroup, false)
        return YashaViewHolderImpl(this, view)
    }
}