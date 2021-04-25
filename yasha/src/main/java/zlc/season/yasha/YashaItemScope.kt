package zlc.season.yasha

import android.view.View
import androidx.viewbinding.ViewBinding

open class YashaItemScope<T : YashaItem>(val containerView: View) {
    lateinit var data: T
    var position: Int = 0

    val map = mutableMapOf<String, Any>()

    fun set(key: String, value: Any) {
        map[key] = value
    }

    inline fun <reified R> get(key: String): R? {
        val find = map[key] ?: return null
        if (find is R) {
            return find
        } else {
            throw IllegalStateException("Type not match!")
        }
    }
}

class YashaItemBindingScope<T : YashaItem, VB : ViewBinding>(val itemBinding: VB) : YashaItemScope<T>(itemBinding.root)

