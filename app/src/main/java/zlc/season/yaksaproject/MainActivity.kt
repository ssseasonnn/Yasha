package zlc.season.yaksaproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import zlc.season.yaksaproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnNormal.setOnClickListener {
            startActivity(Intent(this@MainActivity, DemoActivity::class.java))
        }

        binding.btnViewpager.setOnClickListener {
            startActivity(Intent(this@MainActivity, ViewPager2Activity::class.java))
        }

        binding.btnPage.setOnClickListener {
            startActivity(Intent(this@MainActivity, PagerActivity::class.java))
        }

        binding.btnBanner.setOnClickListener {
            startActivity(Intent(this@MainActivity, BannerActivity::class.java))
        }
    }
}