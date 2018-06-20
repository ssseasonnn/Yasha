![](https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/yaksa.png)


# Yaksa

一个轻量级的RecyclerView工具，让你像Javascript一样渲染Item.

![](https://img.shields.io/badge/language-kotlin-brightgreen.svg)  

*Read this in other languages: [中文](README.ch.md), [English](README.md)* 

众所周知，熟练使用RecyclerView展示列表数据已经是每个Android开发者必备的能力，然而，RecyclerView仍然有它的不足，
那就是过于繁琐，相信每个开发者都有过这样的经历，我们为了展示一个只有单一类型的简单的列表，需要创建一个Adapter，
需要创建一个ViewHolder，而对于具有多个类型的稍微复杂的列表，我们不但需要创建Adapter，同时还要创建多个ViewHolder..

我们一次又一次不断的重复着这样无聊的工作，显得那么的麻木不仁

然而，生活不止眼前的苟且，还有诗和远方！

让我们一起和噩梦一样的Adapter和ViewHolder说声再见，一起来拥抱Yaksa吧!

> Yaksa(夜叉), 提高16点敏捷  15%攻击速度  10%移动速度


### Talk is cheap, show me the code

渲染一个Linear列表：

```kotlin
example_rv.linear {
    renderHeaders(mutableListOf("Header1", "Header2")) {
        HeaderItem(it)
    }
    
    renderFootersByDsl(mutableListOf("Footer1", "Footer2")) { text ->
        xml(R.layout.header_item)
        render { view ->
            view.header_item_tv.text = text
            view.setOnClickListener { }
        }
    }
    
    renderItemsByDsl(dataSource.list) { item ->
        xml(R.layout.list_item)
        render { view ->
            view.list_item_tv.text = item.title
            view.setOnClickListener { toast("Item Clicked") }
        }
    }
}
```

就是这样，没有Adapter,也没有ViewHolder，你只需要专心的渲染Item就好了！

效果图：

<img src="https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/screenshot.png" width="300">


渲染一个Grid列表：

```kotlin
example_rv.grid {
    spanCount(SPAN_COUNT)
    
    renderHeaders(mutableListOf("Header1", "Header2")) {
        HeaderItem(it)
    }
    
    renderFootersByDsl(mutableListOf("Footer1", "Footer2")) { text ->
        xml(R.layout.header_item)
        render { view ->
            view.header_item_tv.text = text
            view.setOnClickListener { }
        }
    }
    
    renderItemsByDsl(dataSource.list) { item ->
        xml(R.layout.list_item)
        render { view ->
            view.list_item_tv.text = item.title
            view.setOnClickListener { toast("Item Clicked") }
        }
    }
}

```

效果图：

<img src="https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/screenshot-grid.png" width="300">


瀑布流? 没问题：

```kotlin
example_rv.stagger {
    spanCount(3)
    
    renderHeaders(mutableListOf("Header1", "Header2")) {
        HeaderItem(it)
    }
    
    renderFootersByDsl(mutableListOf("Footer1", "Footer2")) { text ->
        xml(R.layout.header_item)
        render { view ->
            view.header_item_tv.text = text
            view.setOnClickListener { }
        }
    }
    
    renderItems(dataSource.list) { item ->
        ListItem(item.title, HEIGHTS[Random().nextInt(HEIGHTS.size)].px)
    }
}
```

效果图：

<img src="https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/screenshot-stagger.png" width="300">


### 与LiveData结合发挥强大的实时渲染功效

```kotlin
example_rv.linear {
    headerLiveData.observe {
        renderHeaders(it, clear = true) { headerData ->
            HeaderItem(headerData.header)
        }
    }
    
    footerLiveData.observe {
        renderFootersByDsl(it, clear = true) { footerData ->
            xml(R.layout.header_item)
            render { view ->
                view.header_item_tv.text = footerData.footer
                view.setOnClickListener { }
            }
        }
    }
    
    itemLiveData.observe {
        renderItemsByDsl(it.data, clear = it.isRefresh) { item ->
            xml(R.layout.list_item)
            render { view ->
                view.list_item_tv.text = item.title
                view.setOnClickListener { toast("Item Clicked") }
            }
        }
    }
}
```

这意味着当数据变化时，Yaksa能够实时的进行刷新，真正实现了数据驱动，让你的注意力放在数据和渲染上.


### 强大的扩展能力

自带的Dsl功能有限？没关系，Yaksa强大的定制能力可以让你突破限制，真正让你为所欲为！

```kotlin
// Custom placeholder adapter.
class YaksaPlaceholderAdapter : YaksaCommonStateAdapter() {
    internal var placeholderList = mutableListOf<YaksaItem>()

    fun updatePlaceholderIf(updateImmediately: Boolean) {
        if (updateImmediately) {
            submitList(placeholderList)
        }
    }
}

// Custom placeholder dsl.
class YaksaPlaceholderDsl(override val adapter: YaksaPlaceholderAdapter) : YaksaCommonStateDsl(adapter) {
   
    fun <T> renderPlaceholders(dataSource: List<T>,
                               clear: Boolean = false,
                               updateImmediately: Boolean = true,
                               block: (T) -> YaksaItem) {
        adapter.placeholderList.clearIf(clear)
        dataSource.forEach {
            adapter.placeholderList.add(block(it))
        }
        adapter.updatePlaceholderIf(updateImmediately)
    }

    fun <T> renderPlaceholdersByDsl(dataSource: List<T>,
                                    clear: Boolean = false,
                                    updateImmediately: Boolean = true,
                                    block: YaksaItemDsl.(T) -> Unit) {
        adapter.placeholderList.clearIf(clear)
        dataSource.forEach {
            val dsl = YaksaItemDsl()
            dsl.block(it)
            adapter.placeholderList.add(dsl.internalItem())
        }
        adapter.updatePlaceholderIf(updateImmediately)
    }
}


// Use custom adapter and custom dsl.
example_rv.linear(::YaksaPlaceholderAdapter, ::YaksaPlaceholderDsl) {
    renderPlaceholdersByDsl(List(20) { it }) {
        xml(R.layout.placeholder_item)
    }
    
    itemLiveData.observe {
        renderItemsByDsl(it, clear = true) { headerData ->
            xml(R.layout.header_item)
            render { view ->
                view.header_item_tv.text = title
                view.setOnClickListener { }
            }
        }
    }
}
```

### 现在就开始装备夜叉吧，开启你的超神之路！


### 动图:

<img src="https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/example.gif" width="300">


### 依赖:

```groovy
dependencies {
	implementation 'zlc.season:yaksa:x.x.x'
}
```

(请把上面的x替换成最新的版本号: [![Download](https://api.bintray.com/packages/ssseasonnn/android/Yaksa/images/download.svg)](https://bintray.com/ssseasonnn/android/Yaksa/_latestVersion))



### License

> ```
> Copyright 2018 Season.Zlc
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
> ```