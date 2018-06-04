package zlc.season.yaksa

import android.view.View

class YaksaItemDsl {
    private var resId: Int = 0
    private var block: (view: View) -> Unit = { }

    private var gridSpanSize = 1
    private var staggerFullSpan = false

    fun xml(res: Int) {
        this.resId = res
    }

    fun render(block: (view: View) -> Unit) {
        this.block = block
    }

    fun gridSpanSize(spanSize: Int) {
        this.gridSpanSize = spanSize
    }

    fun staggerFullSpan(fullSpan: Boolean) {
        this.staggerFullSpan = fullSpan
    }

    internal fun internal(): YaksaItem {
        return object : YaksaItem {
            override fun render(view: View) {
                block(view)
            }

            override fun xml(): Int {
                return resId
            }

            override fun gridSpanSize(): Int {
                return gridSpanSize
            }

            override fun staggerFullSpan(): Boolean {
                return staggerFullSpan
            }
        }
    }
}