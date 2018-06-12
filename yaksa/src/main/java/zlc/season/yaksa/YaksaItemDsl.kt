package zlc.season.yaksa

import android.view.View

class YaksaItemDsl {
    private var resId: Int = 0
    private var renderBlock: (view: View) -> Unit = {}
    private var renderBlockX: (position: Int, view: View) -> Unit = { _: Int, _: View -> }

    private var gridSpanSize = 1
    private var staggerFullSpan = false


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

    /**
     * Render item
     *
     * @param block  Call when item render
     */
    fun renderX(block: (position: Int, view: View) -> Unit) {
        this.renderBlockX = block
    }

    /**
     * Only work for Grid, set the span size of this item
     *
     * @param spanSize spanSize
     */
    fun gridSpanSize(spanSize: Int) {
        this.gridSpanSize = spanSize
    }

    /**
     * Only work for Stagger, set the fullSpan of this item
     *
     * @param fullSpan True or false
     */
    fun staggerFullSpan(fullSpan: Boolean) {
        this.staggerFullSpan = fullSpan
    }

    internal fun internal(): YaksaItem {
        if (resId == 0) {
            throw IllegalStateException("You must call the xml() method to pass your layout resource id!")
        }

        return object : YaksaItem {
            override fun render(position: Int, view: View) {
                renderBlock(view)
                renderBlockX(position, view)
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