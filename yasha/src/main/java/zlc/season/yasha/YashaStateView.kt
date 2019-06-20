package zlc.season.yasha

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.layout_yasha_state.view.*
import zlc.season.sange.FetchingState

class YashaStateView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(
                R.layout.layout_yasha_state,
                this,
                true
        )
    }

    fun setState(newState: YashaStateItem) {
        yasha_state_failed_btn.setOnClickListener {
            newState.retry()
        }

        when {
            newState.state == FetchingState.FETCHING -> {
                yasha_state_loading.visibility = View.VISIBLE
                yasha_state_failed_btn.visibility = View.GONE
            }
            newState.state == FetchingState.FETCHING_ERROR -> {
                yasha_state_loading.visibility = View.GONE
                yasha_state_failed_btn.visibility = View.VISIBLE
            }
            newState.state == FetchingState.DONE_FETCHING -> {
                yasha_state_loading.visibility = View.GONE
                yasha_state_failed_btn.visibility = View.GONE
            }
            else -> {
                yasha_state_loading.visibility = View.GONE
                yasha_state_failed_btn.visibility = View.GONE
            }
        }
    }
}