package zlc.season.yaksa

import android.view.View

open class YaksaItemDsl {
    private var resId: Int = 0
    private var renderBlock: (view: View) -> Unit = {}
    private var renderBlockX: (position: Int, view: View) -> Unit = { _: Int, _: View -> }

    private var gridSpanSize = 1
    private var staggerFullSpan = false

    private var onItemAttachWindowBlock: () -> Unit = {}
    private var onItemDetachWindowBlock: () -> Unit = {}
    private var onItemRecycledBlock: () -> Unit = {}

    private var onItemAttachWindowBlockX: (position: Int, view: View) -> Unit = { _: Int, _: View -> }
    private var onItemDetachWindowBlockX: (position: Int, view: View) -> Unit = { _: Int, _: View -> }
    private var onItemRecycledBlockX: (position: Int, view: View) -> Unit = { _: Int, _: View -> }

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

    /**
     * Set a callback that is called when item attach to the window
     */
    fun onItemAttachWindow(block: () -> Unit) {
        this.onItemAttachWindowBlock = block
    }

    /**
     * Set a callback that is called when item attach to the window.
     *
     * This callback is different from [onItemAttachWindow].
     * It has two parameters:  position and view.
     */
    fun onItemAttachWindowX(block: (position: Int, view: View) -> Unit) {
        this.onItemAttachWindowBlockX = block
    }

    /**
     * Set a callback that is called when item detach to the window
     */
    fun onItemDetachWindow(block: () -> Unit) {
        this.onItemDetachWindowBlock = block
    }

    /**
     * Set a callback that is called when item detach to the window.
     *
     * This callback is different from [onItemDetachWindow].
     * It has two parameters:  position and view.
     */
    fun onItemDetachWindowX(block: (position: Int, view: View) -> Unit) {
        this.onItemDetachWindowBlockX = block
    }

    /**
     * Set a callback that is called when item recycled
     */
    fun onItemRecycled(block: () -> Unit) {
        this.onItemRecycledBlock = block
    }

    /**
     * Set a callback that is called when item recycled
     *
     * This callback is different from [onItemRecycled].
     * It has two parameters:  position and view.
     */
    fun onItemRecycledX(block: (position: Int, view: View) -> Unit) {
        this.onItemRecycledBlockX = block
    }

    internal fun internalItem(): YaksaItem {
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

            override fun onItemAttachWindow(position: Int, view: View) {
                super.onItemAttachWindow(position, view)
                onItemAttachWindowBlock()
                onItemAttachWindowBlockX(position, view)
            }

            override fun onItemDetachWindow(position: Int, view: View) {
                super.onItemDetachWindow(position, view)
                onItemDetachWindowBlock()
                onItemDetachWindowBlockX(position, view)
            }

            override fun onItemRecycled(position: Int, view: View) {
                super.onItemRecycled(position, view)
                onItemRecycledBlock()
                onItemRecycledBlockX(position, view)
            }
        }
    }
}