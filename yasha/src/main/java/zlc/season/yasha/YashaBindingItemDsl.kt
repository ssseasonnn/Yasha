package zlc.season.yasha

import android.view.LayoutInflater
import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method

class YashaBindingItemDsl<T : YashaItem, VB : ViewBinding>(private val inflateMethod: Method?) {
    private var inflateFunction: ((LayoutInflater, ViewGroup, Boolean) -> VB)? = null

    private var initScope: YashaBindingScope<T, VB>.() -> Unit = {}

    private var onBind: YashaBindingScope<T, VB>.() -> Unit = {}
    private var onBindPayload: YashaBindingScope<T, VB>.(payload: List<Any>) -> Unit = { _: List<Any> -> }

    private var onAttach: YashaBindingScope<T, VB>.() -> Unit = {}
    private var onDetach: YashaBindingScope<T, VB>.() -> Unit = {}
    private var onRecycled: YashaBindingScope<T, VB>.() -> Unit = {}

    private var gridSpanSize = 1
    private var staggerFullSpan = false

    fun viewBinding(function: (LayoutInflater, ViewGroup, Boolean) -> VB) {
        this.inflateFunction = function
    }

    fun initScope(block: YashaBindingScope<T, VB>.() -> Unit) {
        this.initScope = block
    }

    fun onBind(block: YashaBindingScope<T, VB>.() -> Unit) {
        this.onBind = block
    }

    fun onBindPayload(block: YashaBindingScope<T, VB>.(payload: List<Any>) -> Unit) {
        this.onBindPayload = block
    }

    fun onAttach(block: YashaBindingScope<T, VB>.() -> Unit) {
        this.onAttach = block
    }

    fun onDetach(block: YashaBindingScope<T, VB>.() -> Unit) {
        this.onDetach = block
    }

    fun onRecycled(block: YashaBindingScope<T, VB>.() -> Unit) {
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
        return object : YashaViewHolder(binding.root) {
            val viewHolderScope = YashaBindingScope<T, VB>(binding)

            init {
                viewHolderScope.initScope()
            }

            override fun onBind(position: Int, t: YashaItem) {
                t as T
                viewHolderScope.run {
                    this.data = t
                    this.position = position
                    onBind()
                }
            }

            override fun onBindPayload(position: Int, t: YashaItem, payload: MutableList<Any>) {
                t as T
                viewHolderScope.run {
                    this.data = t
                    this.position = position
                    onBindPayload(payload)
                }
            }

            override fun onAttach(position: Int, t: YashaItem) {
                t as T
                viewHolderScope.run {
                    this.data = t
                    this.position = position
                    onAttach()
                }
            }

            override fun onDetach(position: Int, t: YashaItem) {
                t as T
                viewHolderScope.run {
                    this.data = t
                    this.position = position
                    onDetach()
                }
            }

            override fun onRecycled(position: Int, t: YashaItem) {
                t as T
                viewHolderScope.run {
                    this.data = t
                    this.position = position
                    onRecycled()
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewBinding(viewGroup: ViewGroup): VB {
        return when {
            inflateFunction != null -> inflateFunction!!.invoke(from(viewGroup.context), viewGroup, false) as VB
            inflateMethod != null -> inflateMethod.invoke(null, from(viewGroup.context), viewGroup, false) as VB
            else -> throw IllegalStateException("Can not create ViewBinding!")
        }
    }
}