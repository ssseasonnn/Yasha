![](yasha.png)

<p align="left">
	<img src="https://img.shields.io/badge/kotlin-1.8.0-green"/>
	<a href="https://jitpack.io/#ssseasonnn/Yasha">
		<img src="https://jitpack.io/v/ssseasonnn/Yasha.svg"/>
	</a>
</p>

# Yasha

A DSL library for rendering RecyclerView and ViewPage.

> *Read this in other languages: [中文](README.zh.md), [English](README.md), [Changelog](CHANGELOG.md)*

## Feature introduction：

✅ Support RecyclerView  
✅ Support ViewPager  
✅ Support MultiType  
✅ Support Header and Footer  
✅ Support automatic paging loading  
✅ Support DiffUtil  
✅ Support loading status display  
✅ Support automatic clean up resources

![](yasha_usage.png)

## Prepare

1. Add jitpack to build.gradle
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add Dependencies
```gradle
dependencies {
	implementation 'com.github.ssseasonnn:Yasha:1.1.5'
}
```

## Basic usage

### 1. Render RecyclerView

```kotlin
//Create data class
class RecyclerViewItem(val i: Int, val text: String = "") : YashaItem

//Create DataSource
val dataSource = YashaDataSource()

//Render RecyclerView
recyclerView.linear(dataSource){
    renderBindingItem<RecyclerViewItem, ViewHolderNormalBinding> {
        onBind {
            itemBinding.tvNormalContent.text = "position: $position, data: $data"
        }
    }
}
```

### 2. Render ViewPager

```kotlin
//Create data class
class ViewPagerItem(val i: Int, val text: String = "") : YashaItem

//Create DataSource
val dataSource = YashaDataSource()

//Render ViewPager
viewPager.vertical(dataSource){
    renderBindingItem<ViewPagerItem, ViewHolderNormalBinding> {
        onBind {
            itemBinding.tvNormalContent.text = "position: $position, data: $data"
        }
    }
}
```

## Other configurations

### 1. Render Type

Yasha supports multiple types of RecyclerView, such as list, Grid, Stagger, Pager and custom list types

```kotlin
//Render List
recyclerView.linear(dataSource){
	//Set the direction to vertical list or horizontal list
	orientation(RecyclerView.VERTICAL)
	renderBindingItem<NormalItem, ViewHolderNormalBinding> {}
}

//Render Grid
recyclerView.grid(dataSource){
	//Set the number of columns
	spanCount(2)  
	renderBindingItem<NormalItem, ViewHolderNormalBinding> {  
		//Set the number of columns corresponding to this item
	    gridSpanSize(2)  
	    onBind {}  
	}
}

//Render waterfall flow
recyclerView.stagger(dataSource){
	//Set the number of columns
	spanCount(2)  
	renderBindingItem<NormalItem, ViewHolderNormalBinding> {  
	    staggerFullSpan(true)  
	    onBind {}  
	}
}

//Render Page
recyclerView.pager(dataSource){
	//Register page change callback
	onPageChanged { position, yashaItem, view ->  
	    Toast.makeText(this, "This is page $position", Toast.LENGTH_SHORT).show()  
	}
}

//Render custom layout
recyclerView.custom(customLayoutManager, dataSource){}
```

### 2. DataSource paging load

```kotlin
class CustomDataSource(coroutineScope: CoroutineScope) : YashaDataSource(coroutineScope) {
    var page = 0

    // Called when initializing the load
    override suspend fun loadInitial(): List<YashaItem>? {
        page = 0
    
        val items = mutableListOf<YashaItem>()
        for (i in 0 until 10) {
            items.add(NormalItem(i))
        }

        // Return null to trigger loading failure
        // Return to the empty list to trigger no more
        // Return init data
        return items
    }

    // Called on paging load
    override suspend fun loadAfter(): List<YashaItem>? {
        page++

        if (page % 5 == 0) {
            // Return null to trigger loading failure
            return null  
        }

        val items = mutableListOf<YashaItem>()
        for (i in 0 until 10) {
            items.add(NormalItem(i))
        }

        // Return to the empty list to trigger no more
        return items
    }
}
```

### 3. Render MultiType

```kotlin

//Define data type A
class AItem(val i: Int) : YashaItem

//Define data type B
class BItem(val i:Int) : YashaItem

//Render Items
recyclerView.linear(dataSource){
    //Render Type A
    renderBindingItem<AItem, AItemBinding> {
        onBind {
            //render
            ...
        }
    }
    //Render Type B
    renderBindingItem<BItem, BItemBinding> {
        onBind {
            //render
            ...
        }
    }
}
```

### 4. Header and Footer

DataSource supports the following methods for adding headers and footers:

```kotlin
//Headers
fun addHeader(t: T, position: Int = -1, delay: Boolean = false)
fun addHeaders(list: List<T>, position: Int = -1, delay: Boolean = false) 
fun removeHeader(t: T, delay: Boolean = false) 
fun setHeader(old: T, new: T, delay: Boolean = false)
fun getHeader(position: Int): T
fun clearHeader(delay: Boolean = false)

//Footers
fun addFooter(t: T, position: Int = -1, delay: Boolean = false)
fun addFooters(list: List<T>, position: Int = -1, delay: Boolean = false) 
fun removeFooter(t: T, delay: Boolean = false) 
fun setFooter(old: T, new: T, delay: Boolean = false)
fun getFooter(position: Int): T
fun clearFooter(delay: Boolean = false)
```

### 5. Partial refresh

By rewriting the Diff method of the data class, you can complete efficient local refresh:

```kotlin
//Override Diff method when defining data type
class NormalItem(val i: Int, val text: String = "") : YashaItem {

    override fun areItemsTheSame(other: Differ): Boolean {
        if (other !is NormalItem) return false
        return other.i == i
    }

    override fun areContentsTheSame(other: Differ): Boolean {
        if (other !is NormalItem) return false
        return other.text == text
    }

    //Set up payload
    override fun getChangePayload(other: Differ): Any? {
        if (other !is NormalItem) return null
        return other.text 
    }
}

//Use dataSource to update the new Item data
val oldItem = NormalItem(1, "1")
val newItem = NormalItem(2, "2")
dataSource.setItem(oldItem, newItem)

// Register onBindPayload at render time
recyclerView.linear(dataSource){
    renderBindingItem<NormalItem, ViewHolderNormalBinding> {
        onBind {
            itemBinding.tvNormalContent.text = "position: $position, data: $data"
        }
        
        //Partial refresh use
        onBindPayload {
            //Take out payload for partial refresh
            val payload = it[0]
            if (payload != null) {
                itemBinding.tvNormalContent.text = payload.toString()
            }
        }
    }
}
```

### 6. Custom Load Status

```kotlin
//Use default load state
val dataSource = YashaDataSource(enableDefaultState = true)

//Custom LoadStateItem
class CustomStateItem(val state: Int) : YashaItem
class CustomDataSource : YashaDataSource(enableDefaultState = false) {

    override fun onStateChanged(newState: Int) {
        setState(CustomStateItem(newState))
    }
}

//Render Custom State
recyclerView.linear(dataSource){
    ...
    renderBindingItem<CustomStateItem, CustomStateItemBinding> {
        onBind {
            when (data.state) {
                FetchingState.FETCHING -> {
                    //loading...
                }
                FetchingState.FETCHING_ERROR -> {
                    //loading failed
                }
                FetchingState.DONE_FETCHING -> {
                   //loading complete
                }
                else -> {
                    //other
                }
            }
        }
    }
}
```

### 7. Proguard

Only when using reflection to create ViewBinding, you need to add the following guard rules:

```pro
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
    public static ** inflate(...);
}
```

## License

> ```
> Copyright 2021 Season.Zlc
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
