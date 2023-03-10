package zlc.season.yaksaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import zlc.season.tango.activityScope
import zlc.season.tango.wait
import zlc.season.yaksaproject.databinding.ActivityBannerBinding
import zlc.season.yaksaproject.databinding.LayoutBannerItemBinding
import zlc.season.yasha.YashaDataSource
import zlc.season.yasha.YashaItem
import zlc.season.yasha.horizontal

class BannerActivity : AppCompatActivity() {
    val binding by lazy { ActivityBannerBinding.inflate(layoutInflater) }

    val dataSource = YashaDataSource()

    var autoScrollJob: Job? = null

    private suspend fun ViewPager2.autoScroll() = coroutineScope {
        val recyclerView = (get(0) as RecyclerView)
        recyclerView.itemAnimator = null
        val lm = recyclerView.layoutManager as LinearLayoutManager

        while (isActive) {
            if (canScrollHorizontally(1)) {
                delay(1000L)
                val currentPosition = lm.findFirstCompletelyVisibleItemPosition()
                val viewHolder = recyclerView.findViewHolderForLayoutPosition(currentPosition)
                val height = viewHolder?.itemView?.height ?: 0
                val width = viewHolder?.itemView?.width ?: 0
                recyclerView.smoothScrollBy(width, 0)
                recyclerView.awaitScrollEnd()
            } else {
                if (lm.itemCount > 1) {
                    updateData(lm)
                    wait()
                } else {
                    delay(1000)
                }
            }
        }
    }

    private fun updateData(lm: LinearLayoutManager) {
        val firstPosition = lm.findFirstVisibleItemPosition()
        if (firstPosition != RecyclerView.NO_POSITION) {
            val currentList = dataSource.getItems().toMutableList()
            val second = currentList.subList(0, firstPosition)
            val first = currentList.subList(firstPosition, currentList.size)
            dataSource.clearAll()
            dataSource.addItems(first + second)
        }
    }

    fun startAutoScroll() {
        if (autoScrollJob == null || autoScrollJob?.isActive == false) {
            autoScrollJob = lifecycleScope.launch {
                binding.viewpager.autoScroll()
            }
        }
    }

    fun stopAutoScroll() {
        autoScrollJob?.cancel()
        autoScrollJob = null
    }

    suspend fun RecyclerView.awaitScrollEnd() {
        awaitAnimationFrame()
        if (scrollState == RecyclerView.SCROLL_STATE_IDLE) return

        suspendCancellableCoroutine { continuation ->
            val listener = object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        recyclerView.removeOnScrollListener(this)
                        continuation.resumeWith(Result.success(Unit))
                    }
                }
            }

            continuation.invokeOnCancellation {
                this@awaitScrollEnd.removeOnScrollListener(listener)
            }

            addOnScrollListener(listener)
        }
    }

    suspend fun View.awaitAnimationFrame() = suspendCancellableCoroutine<Unit> { continuation ->
        val runnable = kotlinx.coroutines.Runnable {
            continuation.resumeWith(Result.success(Unit))
        }
        continuation.invokeOnCancellation { removeCallbacks(runnable) }
        postOnAnimation(runnable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.viewpager
        binding.viewpager.horizontal(dataSource) {
            renderBindingItem<BannerItem, LayoutBannerItemBinding> {
                onBind {
                    itemBinding.tvText.text = data.i.toString()
                }
            }
        }

        val list = listOf<YashaItem>(
            BannerItem(0),
            BannerItem(1),
            BannerItem(2),
            BannerItem(3),
        )
        dataSource.addItems(list)


//        handler.postDelayed(runnable, 1000)
        startAutoScroll()
    }

    data class BannerItem(val i: Int) : YashaItem
}