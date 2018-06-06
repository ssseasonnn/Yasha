package zlc.season.yaksa

import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View

/**
 * Item interface
 */
interface YaksaItem {
    /**
     *  Render item
     *
     *  @param position Item position
     *
     *  @param view  Item view
     */
    fun render(position: Int, view: View)

    /**
     * Provide xml layout resource
     */
    fun xml(): Int

    /**
     * Only work for Grid, provide the span size of this item
     */
    fun gridSpanSize(): Int {
        return 1
    }

    /**
     * Only work for Stagger, whether this item needs to occupy the full width
     */
    fun staggerFullSpan(): Boolean {
        return false
    }

    fun onItemAttachWindow(position: Int, view: View) {
        specialStaggerItem(view)
    }


    fun onItemDetachWindow(position: Int, view: View) {

    }

    fun onItemRecycled(position: Int, view: View) {

    }

    private fun specialStaggerItem(view: View) {
        val layoutParams = view.layoutParams
        if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            layoutParams.isFullSpan = staggerFullSpan()
        }
    }
}