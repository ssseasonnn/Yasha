package zlc.season.yasha.compose

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Spliterator
import java.util.function.Consumer
import java.util.function.IntFunction
import java.util.stream.Stream

class DataList<T>(private val realData: List<T>, val onGetCalled: (Int) -> Unit) : List<T> {
    override val size: Int
        get() = realData.size

    override fun contains(element: T): Boolean {
        return realData.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return realData.containsAll(elements)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun forEach(action: Consumer<in T>) {
        super.forEach(action)
    }

    override fun get(index: Int): T {
        onGetCalled(index)
        return realData[index]
    }

    override fun indexOf(element: T): Int {
        return realData.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return realData.isEmpty()
    }

    override fun iterator(): Iterator<T> {
        return realData.iterator()
    }

    override fun lastIndexOf(element: T): Int {
        return realData.lastIndexOf(element)
    }

    override fun listIterator(): ListIterator<T> {
        return realData.listIterator()
    }

    override fun listIterator(index: Int): ListIterator<T> {
        return realData.listIterator(index)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun parallelStream(): Stream<T> {
        return realData.parallelStream()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun spliterator(): Spliterator<T> {
        return realData.spliterator()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun stream(): Stream<T> {
        return realData.stream()
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        return realData.subList(fromIndex, toIndex)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Suppress("Since15")
    @Deprecated(
        "This member is not fully supported by Kotlin compiler, so it may be absent or have different signature in next major version",
        replaceWith = ReplaceWith(""),
        level = DeprecationLevel.WARNING
    )
    override fun <T : Any?> toArray(generator: IntFunction<Array<T>>): Array<T> {
        return realData.toArray(generator)
    }
}