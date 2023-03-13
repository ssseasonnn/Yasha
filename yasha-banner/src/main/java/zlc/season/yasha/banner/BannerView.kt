package zlc.season.yasha.banner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import coil.load
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import zlc.season.tango.lifecycleScope
import zlc.season.yasha.YashaDsl
import zlc.season.yasha.banner.databinding.BannerItemBinding
import zlc.season.yasha.banner.databinding.BannerViewBinding
import zlc.season.yasha.horizontal

class BannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val binding = BannerViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val bannerDataSource = BannerDataSource()
    private var currentPage = 0
    private var autoScrollJob: Job? = null

    private var intervalTime: Long = 3000L

    init {
        binding.viewPager.horizontal(bannerDataSource) {
            renderBannerItem()
        }
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
            }
        })
    }

    private fun YashaDsl.renderBannerItem() {
        renderBindingItem<BannerItem, BannerItemBinding> {
            onBind {
                itemBinding.bannerIcon.load(data.image)
            }
        }
    }

    fun setIntervalTime(interval: Long) {
        this.intervalTime = interval
    }

    fun setData(data: List<BannerItem>, startScroll: Boolean = true) {
        bannerDataSource.setData(data)
        binding.viewPager.setCurrentItem(bannerDataSource.getInitPosition(), false)

        if (startScroll) {
            startAutoScroll()
        }
    }

    fun startAutoScroll() {
        if (autoScrollJob == null || autoScrollJob?.isActive == false) {
            autoScrollJob = lifecycleScope.launch {
                binding.viewPager.autoScroll()
            }
        }
    }

    fun stopAutoScroll() {
        autoScrollJob?.cancel()
        autoScrollJob = null
    }

    private suspend fun ViewPager2.autoScroll() = coroutineScope {
        while (isActive) {
            delay(intervalTime)
            currentItem = currentPage++
        }
    }
}