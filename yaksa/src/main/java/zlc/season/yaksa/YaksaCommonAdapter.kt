package zlc.season.yaksa

import android.os.Handler
import android.os.Looper

open class YaksaCommonAdapter : YaksaAdapter() {
    internal var headerList = mutableListOf<YaksaItem>()
    internal var itemList = mutableListOf<YaksaItem>()
    internal var footerList = mutableListOf<YaksaItem>()
    internal var extraList = mutableListOf<YaksaItem>()
    internal var placeholderList = mutableListOf<YaksaItem>()

    private var state: YaksaState? = null

    protected open fun items(): List<YaksaItem> {
        val result = mutableListOf<YaksaItem>()
        result.addAll(headerList)
        result.addAll(itemList)
        result.addAll(footerList)
        result.addAll(extraList)
        result.addAll(placeholderList)
        return result
    }

    @Synchronized
    override fun update() {
        submitList(items())
    }

    internal fun setState(newState: YaksaState) {
        val hadStateItem = hasStateItem()

        val previousState = this.state
        this.state = newState

        val hasStateItem = hasStateItem()

        if (hadStateItem != hasStateItem) {
            if (hadStateItem) {
                post { notifyItemRemoved(super.getItemCount()) }
            } else {
                post { notifyItemInserted(super.getItemCount()) }
            }
        } else if (hasStateItem && previousState != newState) {
            post { notifyItemChanged(itemCount - 1) }
        }
    }

    private fun post(block: () -> Unit) {
        Handler(Looper.getMainLooper()).post {
            block()
        }
    }

    private fun hasStateItem(): Boolean {
        val currentState = state
        return currentState != null && currentState.isShowStateItem()
    }

    override fun getItem(position: Int): YaksaItem {
        if (hasStateItem() && position == itemCount - 1) {
            return state!!.renderStateItem()
        }
        return super.getItem(position)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasStateItem()) 1 else 0
    }
}