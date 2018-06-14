package zlc.season.yaksa

import android.os.Handler
import android.os.Looper

class YaksaRealAdapter : YaksaAdapter() {
    private var headerList = mutableListOf<YaksaItem>()
    private var itemList = mutableListOf<YaksaItem>()
    private var footerList = mutableListOf<YaksaItem>()
    private var extraList = mutableListOf<YaksaItem>()

    private var state: YaksaState? = null

    private fun setState(newState: YaksaState) {
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

    internal fun stash(dsl: YaksaDsl) {
        this.headerList = dsl.headerList
        this.itemList = dsl.itemList
        this.footerList = dsl.footerList
        this.extraList = dsl.extraList

        if (dsl.state != null) {
            this.setState(dsl.state!!)
        }
    }

    internal fun pop(dsl: YaksaDsl) {
        dsl.headerList = this.headerList
        dsl.itemList = this.itemList
        dsl.footerList = this.footerList
        dsl.extraList = this.extraList
    }
}