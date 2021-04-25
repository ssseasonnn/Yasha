package zlc.season.yasha

import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method

class YashaBindingItemDsl<T : YashaItem, VB : ViewBinding>(private val inflateMethod: Method?) {
    private var inflateFunction: ((LayoutInflater, ViewGroup, Boolean) -> VB)? = null
    private var gridSpanSize = 1
    private var staggerFullSpan = false

    internal var initScopeItem: YashaItemBindingScope<T, VB>.() -> Unit = {}

    internal var onBind: YashaItemBindingScope<T, VB>.() -> Unit = {}
    internal var onBindPayload: YashaItemBindingScope<T, VB>.(payload: List<Any>) -> Unit = { _: List<Any> -> }

    internal var onAttach: YashaItemBindingScope<T, VB>.() -> Unit = {}
    internal var onDetach: YashaItemBindingScope<T, VB>.() -> Unit = {}
    internal var onRecycled: YashaItemBindingScope<T, VB>.() -> Unit = {}

    fun viewBinding(function: (LayoutInflater, ViewGroup, Boolean) -> VB) {
        this.inflateFunction = function
    }

    fun initScope(block: YashaItemBindingScope<T, VB>.() -> Unit) {
        this.initScopeItem = block
    }

    fun onBind(block: YashaItemBindingScope<T, VB>.() -> Unit) {
        this.onBind = block
    }

    fun onBindPayload(block: YashaItemBindingScope<T, VB>.(payload: List<Any>) -> Unit) {
        this.onBindPayload = block
    }

    fun onAttach(block: YashaItemBindingScope<T, VB>.() -> Unit) {
        this.onAttach = block
    }

    fun onDetach(block: YashaItemBindingScope<T, VB>.() -> Unit) {
        this.onDetach = block
    }

    fun onRecycled(block: YashaItemBindingScope<T, VB>.() -> Unit) {
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
        val binding = getViewBinding(viewGroup)
        return YashaViewHolderBindingImpl(this, binding)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewBinding(viewGroup: ViewGroup): VB {
        return when {
            inflateFunction != null -> inflateFunction!!.invoke(from(viewGroup.context), viewGroup, false)
            inflateMethod != null -> inflateMethod.invoke(null, from(viewGroup.context), viewGroup, false) as VB
            else -> throw IllegalStateException("Can not create ViewBinding!")
        }
    }
}