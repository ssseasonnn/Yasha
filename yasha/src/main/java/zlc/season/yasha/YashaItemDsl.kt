package zlc.season.yasha

import android.view.View
import android.view.ViewGroup

class YashaItemDsl {
    var resId: Int = 0
    var renderBlock: (view: View) -> Unit = {}
    var clazz: Class<*>? = null

    fun item(clazz: Class<*>) {
        this.clazz = clazz
    }

    /**
     * Set item xml layout resource
     */
    fun xml(res: Int) {
        this.resId = res
    }

    /**
     * Render item
     *
     * @param block  Call when item render
     */
    fun render(block: (view: View) -> Unit) {
        this.renderBlock = block
    }

    fun internalItem(viewGroup: ViewGroup): YashaViewHolder {

        return object : YashaViewHolder(viewGroup, this.resId) {
            override fun onBind(t: YaksaItem) {
                super.onBind(t)
                renderBlock(t)
            }
        }
    }
}