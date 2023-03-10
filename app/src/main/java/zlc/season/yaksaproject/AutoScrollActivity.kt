package zlc.season.yaksaproject

import android.content.Context
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import zlc.season.tango.activityScope
import zlc.season.tango.px
import zlc.season.tango.wait
import zlc.season.yaksaproject.databinding.ViewBannerBinding
import zlc.season.yaksaproject.databinding.ViewBannerItemBinding
import zlc.season.yasha.YashaDataSource
import zlc.season.yasha.YashaItem
import zlc.season.yasha.linear

class AutoScrollActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_scroll)
    }
}

class BannerItem : YashaItem

class BannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    val binding = ViewBannerBinding.inflate(LayoutInflater.from(context), this, true)
    val dataSource = YashaDataSource()

    var autoScrollJob: Job? = null
    var isRepeat: Boolean = false

    init {
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.linear(dataSource) {
            renderBindingItem<BannerItem, ViewBannerItemBinding> {
                viewBinding(ViewBannerItemBinding::inflate)
                onBind {
                    itemBinding.image.setImageResource(R.color.colorAccent)
                }
            }
        }

        render(
            listOf(
                BannerItem(),
                BannerItem(),
                BannerItem(),
                BannerItem(),
                BannerItem(),
                BannerItem(),
                BannerItem(),
                BannerItem(),
                BannerItem(),
                BannerItem(),
                BannerItem(),
                BannerItem()
            )
        )
    }

    private suspend fun RecyclerView.autoScroll() = coroutineScope {
        val lm = layoutManager as LinearLayoutManager

        while (isActive) {
            if (canScrollVertically(1)) {
                delay(3000L)
                val currentPosition = lm.findFirstCompletelyVisibleItemPosition()
                val viewHolder = findViewHolderForLayoutPosition(currentPosition)
                val height = viewHolder?.itemView?.height ?: 0

                smoothScrollBy(0, height)
                awaitScrollEnd()
            } else {
                wait()
                if (lm.itemCount > 1) {
                    updateData(lm)
                } else {
                    delay(3000)
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
            autoScrollJob = activityScope.launch {
                binding.recyclerView.autoScroll()
            }
        }
    }

    fun stopAutoScroll() {
        autoScrollJob?.cancel()
        autoScrollJob = null
    }

    fun render(data: List<BannerItem>) {
        if (data.isEmpty()) return

        if (dataSource.itemSize() == 0) {
            val temp = mutableListOf<YashaItem>()
            temp.addAll(data)
            if (temp.size == 1) {
                temp.addAll(data)
            }
            dataSource.addItems(temp)
            isRepeat = true
            startAutoScroll()
        } else {
            if (dataSource.itemSize() == 2 && isRepeat) {
                activityScope.launch {
                    autoScrollJob?.cancelAndJoin()
                    binding.recyclerView.awaitScrollEnd()
                    dataSource.removeItemAt(0)
                    dataSource.addItems(data)
                    startAutoScroll()
                    isRepeat = false
                }
            } else {
                dataSource.addItems(data)
                startAutoScroll()
            }
        }
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
}