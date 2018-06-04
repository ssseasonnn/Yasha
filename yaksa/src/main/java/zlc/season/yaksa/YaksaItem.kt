package zlc.season.yaksa

import android.view.View

interface YaksaItem {
    fun render(view: View)

    fun xml(): Int
}