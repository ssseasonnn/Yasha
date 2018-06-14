package zlc.season.yaksaproject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import kotlin.math.absoluteValue

class NestedRecyclerView : RecyclerView {
    var lastX = 0f
    var lastY = 0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = ev.x
                lastY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (ev.x - lastX).absoluteValue
                val dy = (ev.y - lastY).absoluteValue
                if (dx > dy) {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(false)
        }
        return super.onInterceptTouchEvent(ev)
    }
}