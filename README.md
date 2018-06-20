![](https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/yaksa.png)


# Yaksa

A lightweight RecyclerView tool that lets you render items like Javascript.

![](https://img.shields.io/badge/language-kotlin-brightgreen.svg)  

*Read this in other languages: [中文](README.ch.md), [English](README.md)* 

As we all know, using RecyclerView to display list data is a must-have feature for every Android developer.

However, RecyclerView still has its shortcomings, that is too cumbersome.

I believe every developer has had this experience: 

To show a simple list of only a single type, we need to create an Adapter and we need to create a ViewHolder,

for a slightly more complicated list with multiple types, we need not only to create an Adapter, but also to create multiple ViewHolders

We repeated such boring work again and again and again, and it seemed so insensitive.

However, there is more to life than just present, poetry and distance!

Let's say goodbye to the same Adapter and ViewHolder as the nightmare, and embrace Yaksa together!

> Yaksa(夜叉), Increases 16 Agility 15% Attack Speed ​​10% Movement Speed


### Talk is cheap, show me the code

Render a Linear list:

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

That's it, there is no Adapter, no ViewHolder, you just need to concentrate on rendering the Item just fine!

Output:

<img src="https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/screenshot.png" width="300">


Render a Grid list:

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

Output:

<img src="https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/screenshot-grid.png" width="300">


Waterfall flow? No problem:

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

Output:

<img src="https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/screenshot-stagger.png" width="300">


### Combines powerful real-time rendering with LiveData

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

This means that when the data changes, Yaksa can refresh in real time, 
truly data-driven, let your attention on data and rendering.


### Strong ability to expand

Dsl comes with limited features? It doesn't matter, Yaksa's powerful 
customization capabilities can allow you to break through the limits 
and really let you do whatever you want!

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

### Start yaksa now and start your super-god!


### Finally Gif:

<img src="https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/example.gif" width="300">


### Add to your project build.gradle file:

```groovy
dependencies {
	implementation 'zlc.season:yaksa:x.x.x'
}
```

(Please replace x with the latest version numbers: [![Download](https://api.bintray.com/packages/ssseasonnn/android/Yaksa/images/download.svg)](https://bintray.com/ssseasonnn/android/Yaksa/_latestVersion))

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