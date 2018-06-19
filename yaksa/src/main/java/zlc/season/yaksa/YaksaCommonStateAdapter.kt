package zlc.season.yaksa

import android.os.Handler
import android.os.Looper
import android.support.v7.widget.RecyclerView

open class YaksaCommonStateAdapter : YaksaCommonAdapter() {

    init {
    }

    private var state: YaksaState? = null

    internal fun setState(newState: YaksaState) {
        println("set state")
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

    @Synchronized
    override fun update() {
        println("update")
        super.update()
    }

    private fun post(block: () -> Unit) {
//        Handler(Looper.getMainLooper()).post {
//            block()
//        }
        if (!recyclerView.isComputingLayout){
            block()
        }else{
            Handler(Looper.getMainLooper()).postDelayed({
                block()
            },1000)
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

    lateinit var recyclerView:RecyclerView
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }
}