package zlc.season.yaksa

import android.os.Handler
import android.os.Looper
import android.support.v7.widget.RecyclerView


open class YaksaCommonStateAdapter : YaksaCommonAdapter() {
    private var state: YaksaState? = null
    lateinit var recyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    internal fun setState(newState: YaksaState) {
        val hadStateItem = hasStateItem()

        val previousState = this.state
        this.state = newState

        val hasStateItem = hasStateItem()

        if (hadStateItem != hasStateItem) {
            if (hadStateItem) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasStateItem && previousState != newState) {
            postNotify {
                notifyItemChanged(itemCount - 1)
            }
        }
    }

    private fun postNotify(delayMillis: Long = 0L, block: () -> Unit) {
        Handler(Looper.myLooper()).postDelayed({
            if (!recyclerView.isComputingLayout) {
                block()
            } else {
                postNotify(delayMillis + 500, block)
            }
        }, delayMillis)
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
        return if (hasStateItem()) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }
    }
}