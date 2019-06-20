![](https://raw.githubusercontent.com/ssseasonnn/Yasha/master/yasha.png)


# Yasha


[![](https://jitpack.io/v/ssseasonnn/Yasha.svg)](https://jitpack.io/#ssseasonnn/Yasha)

### How to use

Step 1. Add the JitPack repository to your build file
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency

```gradle
dependencies {
    // replace xyz to latest version number
	implementation 'com.github.ssseasonnn:Yasha:xyz'
}
```

Step 3. Usage


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


### License

> ```
> Copyright 2019 Season.Zlc
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