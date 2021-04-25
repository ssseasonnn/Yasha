package zlc.season.yasha

import android.view.View
import androidx.viewbinding.ViewBinding
import zlc.season.sange.SangeViewHolder

open class YashaViewHolder(containerView: View) : SangeViewHolder<YashaItem>(containerView)

@Suppress("UNCHECKED_CAST")
class YashaViewHolderImpl<T : YashaItem>(private val itemDsl: YashaItemDsl<T>, view: View) : YashaViewHolder(view) {
    private val viewHolderScope = YashaItemScope<T>(view)

    init {
        with(itemDsl) {
            viewHolderScope.initItemScope()
        }
    }

    override fun onBind(position: Int, t: YashaItem) {
        t as T
        viewHolderScope.run {
            this.data = t
            this.position = position
            with(itemDsl) {
                viewHolderScope.onBind()
            }
        }
    }

    override fun onBindPayload(position: Int, t: YashaItem, payload: MutableList<Any>) {
        t as T
        viewHolderScope.run {
            this.data = t
            this.position = position
            with(itemDsl) {
                viewHolderScope.onBindPayload(payload)
            }
        }
    }

    override fun onAttach(position: Int, t: YashaItem) {
        t as T
        viewHolderScope.run {
            this.data = t
            this.position = position
            with(itemDsl) {
                viewHolderScope.onAttach()
            }
        }
    }

    override fun onDetach(position: Int, t: YashaItem) {
        t as T
        viewHolderScope.run {
            this.data = t
            this.position = position
            with(itemDsl) {
                viewHolderScope.onDetach()
            }
        }
    }

    override fun onRecycled(position: Int, t: YashaItem) {
        t as T
        viewHolderScope.run {
            this.data = t
            this.position = position
            with(itemDsl) {
                viewHolderScope.onRecycled()
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class YashaViewHolderBindingImpl<T : YashaItem, VB : ViewBinding>(
        private val bindingItemDsl: YashaBindingItemDsl<T, VB>,
        binding: VB
) : YashaViewHolder(binding.root) {

    private val viewHolderScope = YashaItemBindingScope<T, VB>(binding)

    init {
        with(bindingItemDsl) {
            viewHolderScope.initScopeItem()
        }
    }

    override fun onBind(position: Int, t: YashaItem) {
        t as T
        viewHolderScope.run {
            this.data = t
            this.position = position
            with(bindingItemDsl) {
                viewHolderScope.onBind()
            }
        }
    }

    override fun onBindPayload(position: Int, t: YashaItem, payload: MutableList<Any>) {
        t as T
        viewHolderScope.run {
            this.data = t
            this.position = position
            with(bindingItemDsl) {
                viewHolderScope.onBindPayload(payload)
            }
        }
    }

    override fun onAttach(position: Int, t: YashaItem) {
        t as T
        viewHolderScope.run {
            this.data = t
            this.position = position
            with(bindingItemDsl) {
                viewHolderScope.onAttach()
            }
        }
    }

    override fun onDetach(position: Int, t: YashaItem) {
        t as T
        viewHolderScope.run {
            this.data = t
            this.position = position
            with(bindingItemDsl) {
                viewHolderScope.onDetach()
            }
        }
    }

    override fun onRecycled(position: Int, t: YashaItem) {
        t as T
        viewHolderScope.run {
            this.data = t
            this.position = position
            with(bindingItemDsl) {
                viewHolderScope.onRecycled()
            }
        }
    }
}
