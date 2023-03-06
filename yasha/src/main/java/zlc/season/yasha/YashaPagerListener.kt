package zlc.season.yasha

import android.view.ContextThemeWrapper
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.Result.Companion.success

class YashaPagerListener(
    private val recyclerView: RecyclerView,
    private val onPageChanged: (position: Int, item: YashaItem, itemView: View) -> Unit
) : RecyclerView.OnScrollListener() {
    private var job: Job? = null
    private var snapView: View? = null
    private val snapHelper = PagerSnapHelper()

    init {
        snapHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                recyclerView.viewScope().launch {
                    val lm = recyclerView.layoutManager as LinearLayoutManager
                    val currentIndex = lm.findFirstCompletelyVisibleItemPosition()
                    if (currentIndex == positionStart) {
                        if (positionStart == 0) {
                            recyclerView.wait()
                            handlePageChange(0)
                        } else {
                            recyclerView.scrollToPosition(positionStart)
                            recyclerView.wait()
                            handlePageChange(positionStart)
                        }
                    }
                }
            }
        })
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            notifyListenerIfNeeded(recyclerView)
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifyListenerIfNeeded(recyclerView)
        }
    }

    private fun handlePageChange(currentPosition: Int) {
        val itemView = getItemView(currentPosition)
        val item = getItem(currentPosition)
        if (itemView != null && item != null) {
            onPageChanged(currentPosition, item, itemView)
        }
    }

    private fun getItemView(position: Int): View? {
        val adapter = recyclerView.adapter ?: return null
        if (adapter is YashaAdapter && position in 0 until adapter.itemCount) {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position) ?: return null
            return viewHolder.itemView
        }
        return null
    }


    private fun getItem(position: Int): YashaItem? {
        val adapter = recyclerView.adapter ?: return null
        if (adapter is YashaAdapter && position in 0 until adapter.itemCount) {
            return adapter.getItem(position)
        }
        return null
    }

    private fun getSnapView(recyclerView: RecyclerView): View? {
        val layoutManager = recyclerView.layoutManager ?: return null
        return snapHelper.findSnapView(layoutManager)
    }

    private fun getSnapPosition(snapView: View): Int {
        val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    private fun notifyListenerIfNeeded(recyclerView: RecyclerView) {
        job?.cancel()
        job = recyclerView.viewScope().launch {
            val newSnapView = getSnapView(recyclerView)
            if (newSnapView != null && snapView != newSnapView) {
                val position = getSnapPosition(newSnapView)
                if (position == RecyclerView.NO_POSITION) return@launch
                if (isActive) {
                    handlePageChange(position)
                    snapView = newSnapView
                }
            }
        }
    }

    private fun View.viewScope(): CoroutineScope {
        val context = when (val tempContext = context) {
            is LifecycleOwner -> tempContext
            is ContextThemeWrapper -> tempContext.baseContext
            else -> throw IllegalStateException("The context of view should inherit LifecycleOwner.")
        }

        return if (context != null && context is LifecycleOwner) {
            context.lifecycleScope
        } else {
            throw IllegalStateException("The context of view should inherit LifecycleOwner.")
        }
    }

    private suspend fun View.wait() = suspendCancellableCoroutine {
        val runnable = Runnable {
            if (it.isActive) {
                it.resumeWith(success(Unit))
            }
        }
        it.invokeOnCancellation {
            removeCallbacks(runnable)
        }
        post(runnable)
    }
}