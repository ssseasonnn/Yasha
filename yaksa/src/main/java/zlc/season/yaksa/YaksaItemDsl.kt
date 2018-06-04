package zlc.season.yaksa

import android.view.View

class YaksaItemDsl {
    var resId: Int = 0
    var block: (view: View) -> Unit = {}

    fun xml(res: Int) {
        this.resId = res
    }

    fun render(block: (view: View) -> Unit) {
        this.block = block
    }

    fun internal(): YaksaItem {
        return object : YaksaItem {
            override fun render(view: View) {
                block(view)
            }

            override fun xml(): Int {
                return resId
            }
        }
    }
}