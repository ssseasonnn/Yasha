package zlc.season.yaksa

import android.view.View

interface YaksaItem {
    fun render(view: View)

    fun xml(): Int

    fun injectPosition(position: Int) {}

    fun gridSpanSize(): Int {
        return 1
    }

    fun staggerFullSpan(): Boolean {
        return false
    }
}