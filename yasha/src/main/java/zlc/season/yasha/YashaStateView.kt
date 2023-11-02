package zlc.season.yasha

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import zlc.season.sange.datasource.FetchState

class YashaStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var failedBtn: Button
    private var loading: ProgressBar

    init {
        LayoutInflater.from(context).inflate(
            R.layout.layout_yasha_state,
            this,
            true
        )

        failedBtn = findViewById(R.id.yasha_state_failed_btn)
        loading = findViewById(R.id.yasha_state_loading)
    }

    fun setState(newState: YashaStateItem) {
        failedBtn.setOnClickListener {
            newState.retry()
        }

        when (newState.state) {
            FetchState.Fetching -> {
                loading.visibility = View.VISIBLE
                failedBtn.visibility = View.GONE
            }

            FetchState.FetchingError -> {
                loading.visibility = View.GONE
                failedBtn.visibility = View.VISIBLE
            }

            FetchState.DoneFetching -> {
                loading.visibility = View.GONE
                failedBtn.visibility = View.GONE
            }

            else -> {
                loading.visibility = View.GONE
                failedBtn.visibility = View.GONE
            }
        }
    }
}