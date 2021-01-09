package zlc.season.yaksaproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import zlc.season.yaksaproject.databinding.ActivityDemoBinding
import zlc.season.yaksaproject.databinding.ViewHolderFooterBinding
import zlc.season.yaksaproject.databinding.ViewHolderHeaderBinding
import zlc.season.yaksaproject.databinding.ViewHolderNormalBinding
import zlc.season.yasha.linear

class DemoActivity : AppCompatActivity() {
    private val demoViewModel by lazy {
        ViewModelProvider(this)[DemoViewModel::class.java]
    }
    private val binding by lazy { ActivityDemoBinding.inflate(layoutInflater) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.linear(demoViewModel.dataSource) {
            // 使用反射构造ViewBinding
            renderBindingItem<NormalItem, ViewHolderNormalBinding> {
                onBind {
                    itemBinding.tvNormalContent.text = "position: $position, data: $data"
                }
            }

            renderBindingItem<HeaderItem, ViewHolderHeaderBinding> {
                onBind {
                    itemBinding.tvHeaderContent.text = "position: $position, data: $data"
                }
            }

            renderBindingItem<FooterItem, ViewHolderFooterBinding> {
                onBind {
                    itemBinding.tvFooterContent.text = "position: $position, data: $data"
                }
            }

            // 不使用反射构造ViewBinding
            renderBindingItem<HeaderItem, ViewHolderHeaderBinding>("AAA") {
                viewBinding(ViewHolderHeaderBinding::inflate)
                onBind {
                    itemBinding.tvHeaderContent.text = "position: $position, data: $data"
                }
                onBindPayload {
                    itemBinding.tvHeaderContent.text = "position: $position, data: $data"
                }
            }

            // 不使用ViewBinding
            renderItem<HeaderItem>("BBB") {
                res(R.layout.view_holder_header)
                onBind {
                    val itemBinding = ViewHolderHeaderBinding.bind(containerView)
                    itemBinding.tvHeaderContent.text = "position: $position, data: $data"
                }
                onBindPayload {
                    val itemBinding = ViewHolderHeaderBinding.bind(containerView)
                    itemBinding.tvHeaderContent.text = "position: $position, data: $data"
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