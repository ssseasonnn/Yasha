package zlc.season.yaksaproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import zlc.season.yaksaproject.databinding.ActivityBannerBinding
import zlc.season.yasha.banner.BannerItem

class BannerActivity : AppCompatActivity() {
    val binding by lazy { ActivityBannerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val data = listOf<BannerItem>(
            BannerItem(0, "https://img0.baidu.com/it/u=981218435,2998857702&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto?sec=1678813200&t=6b1e6ab4b44962092c51538793726165", ""),
            BannerItem(1, "https://img0.baidu.com/it/u=981218435,2998857702&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto?sec=1678813200&t=6b1e6ab4b44962092c51538793726165", ""),
            BannerItem(2, "https://img0.baidu.com/it/u=981218435,2998857702&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto?sec=1678813200&t=6b1e6ab4b44962092c51538793726165", ""),
            BannerItem(3, "https://img0.baidu.com/it/u=981218435,2998857702&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto?sec=1678813200&t=6b1e6ab4b44962092c51538793726165", ""),
        )
        binding.bannerView.setData(data)
    }
}