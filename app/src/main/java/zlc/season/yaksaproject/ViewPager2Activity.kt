package zlc.season.yaksaproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import zlc.season.yaksaproject.databinding.*
import zlc.season.yasha.YashaStateItem
import zlc.season.yasha.vertical

class ViewPager2Activity : AppCompatActivity() {
    private val demoViewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DemoViewModel(false) as T
            }
        })[DemoViewModel::class.java]
    }
    private val binding by lazy { ActivityViewpager2Binding.inflate(layoutInflater) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUi(savedInstanceState != null)
    }

    @SuppressLint("SetTextI18n")
    private fun initUi(isRecreate: Boolean) {
        binding.viewPager.vertical(demoViewModel.dataSource, shouldInvalidate = !isRecreate) {
            // 使用反射构造ViewBinding
            renderBindingItem<NormalItem, ViewPagerNormalBinding> {
                onBind {
                    itemBinding.tvNormalContent.text = "position: $position, data: $data"
                }
            }

            renderBindingItem<HeaderItem, ViewPagerHeaderBinding> {
                onBind {
                    itemBinding.tvHeaderContent.text = "position: $position, data: $data"
                }
            }

            renderBindingItem<FooterItem, ViewPagerFooterBinding> {
                onBind {
                    itemBinding.tvFooterContent.text = "position: $position, data: $data"
                }
            }

            // 不使用反射构造ViewBinding
            renderBindingItem<HeaderItem, ViewPagerHeaderBinding>("AAA") {
                viewBinding(ViewPagerHeaderBinding::inflate)
                onBind {
                    itemBinding.tvHeaderContent.text = "position: $position, data: $data"
                }
                onBindPayload {
                    itemBinding.tvHeaderContent.text = "position: $position, data: $data"
                }
            }

            // 不使用ViewBinding
            renderItem<HeaderItem>("BBB") {
                res(R.layout.view_pager_header)
                onBind {
                    val itemBinding = ViewPagerHeaderBinding.bind(containerView)
                    itemBinding.tvHeaderContent.text = "position: $position, data: $data"
                }
                onBindPayload {
                    val itemBinding = ViewPagerHeaderBinding.bind(containerView)
                    itemBinding.tvHeaderContent.text = "position: $position, data: $data"
                }
            }

            renderBindingItem<YashaStateItem, ViewPagerStateBinding> {
                viewBinding(ViewPagerStateBinding::inflate)
                onBind {
                    itemBinding.stateLoading.visibility = View.VISIBLE
                }
            }
        }


        binding.swipeRefreshLayout.setOnRefreshListener {
            demoViewModel.refresh()
        }

        demoViewModel.refresh.observe(this, Observer {
            if (it == null) return@Observer
            binding.swipeRefreshLayout.isRefreshing = it
        })

        binding.button.setOnClickListener {
            demoViewModel.changeHeader()
        }
    }
}