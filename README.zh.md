![](yasha.png)

[![](https://jitpack.io/v/ssseasonnn/Yasha.svg)](https://jitpack.io/#ssseasonnn/Yasha)

*Read this in other languages: [中文](README.zh.md), [English](README.md), [Changelog](CHANGELOG.md)*

# Yasha (夜叉)


![](yasha_usage.png)


## Prepare

1. 添加jitpack到build.gradle
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. 添加依赖

```gradle
dependencies {
	implementation 'com.github.ssseasonnn:Yasha:1.1.5'
}
```


## First Blood

渲染一个RecyclerView分几步？

```kotlin

//定义数据类型
class NormalItem(val i: Int, val text: String = "") : YashaItem

//创建DataSource并添加数据
val dataSource = YashaDataSource()
val items = mutableListOf<YashaItem>()
for (i in 0 until 10) {
    items.add(NormalItem(i))
}
dataSource.addItems(items)

//渲染Item
recyclerView.linear(dataSource){
    // 使用反射构造ViewBinding
    renderBindingItem<NormalItem, ViewHolderNormalBinding> {
        onBind {
            itemBinding.tvNormalContent.text = "position: $position, data: $data"
        }
    }
    // 或者
    // 不使用反射构造ViewBinding
    renderBindingItem<NormalItem, ViewHolderNormalBinding>() {
        viewBinding(ViewHolderNormalBinding::inflate)
        onBind {
            itemBinding.tvNormalContent.text = "position: $position, data: $data"
        }
    }
}
```

> 使用反射创建ViewBinding时，请添加以下proguard rule：

```pro
-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
    public static ** inflate(...);
}
```

## Double Kill

分页？Easy!

```kotlin
// 继承YashaDataSource并重写**loadInitial**和**loadAfter**方法即可
class CustomDataSource(coroutineScope: CoroutineScope) : YashaDataSource(coroutineScope) {
    var page = 0

    // 初始化加载时调用，位于IO线程
    override suspend fun loadInitial(): List<YashaItem>? {
        page = 0
    
        val items = mutableListOf<YashaItem>()
        for (i in 0 until 10) {
            items.add(NormalItem(i))
        }

        // 返回null触发加载失败
        // 返回空列表触发没有更多
        return items
    }

    // 分页加载时调用，位于IO线程
    override suspend fun loadAfter(): List<YashaItem>? {
        page++

        //模拟加载失败
        if (page % 5 == 0) {
            // 返回null触发加载失败
            return null  
        }

        val items = mutableListOf<YashaItem>()
        for (i in 0 until 10) {
            items.add(NormalItem(i))
        }

        // 返回空列表触发没有更多
        return items
    }
}
```

> 你只需要定义好初始化加载和分页加载的逻辑，夜叉会在合适的时候自动调用，安心的做个甩手掌柜吧

## Triple Kill

多种viewType？小case啦

```kotlin

//定义数据类型A
class AItem(val i: Int) : YashaItem

//定义数据类型B
class BItem(val i:Int) : YashaItem

//添加不同类型数据
val dataSource = YashaDataSource()
val items = mutableListOf<YashaItem>()
for (i in 0 until 5) {
    items.add(AItem(i))
}
for (i in 5 until 10){
    items.add(BItem(i))
}
dataSource.addItems(items)

//渲染Item
recyclerView.linear(dataSource){
    //渲染AItem
    renderBindingItem<AItem, AItemBinding> {
        onBind {
            //render
            ...
        }
    }
    //渲染BItem
    renderBindingItem<BItem, BItemBinding> {
        onBind {
            //render
            ...
        }
    }
}
```

Header 和 Footer？没问题，DataSource都支持

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

## Ultra kill

局部刷新? 往下面看

```kotlin
//定义数据类型时，重写Diff方法
class NormalItem(val i: Int, val text: String = "") : YashaItem {

    override fun areItemsTheSame(other: Differ): Boolean {
        if (other !is NormalItem) return false
        return other.i == i
    }

    override fun areContentsTheSame(other: Differ): Boolean {
        if (other !is NormalItem) return false
        return other.text == text
    }

    //设置payload
    override fun getChangePayload(other: Differ): Any? {
        if (other !is NormalItem) return null
        return other.text 
    }
}

//dataSource更新新的Item数据
val oldItem = NormalItem(1, "1")
val newItem = NormalItem(2, "2")
dataSource.setItem(oldItem, newItem)

// 渲染时注册onBindPayload
recyclerView.linear(dataSource){
    renderBindingItem<NormalItem, ViewHolderNormalBinding> {
        onBind {
            itemBinding.tvNormalContent.text = "position: $position, data: $data"
        }
        onBindPayload {
            //取出payload进行局部刷新
            val payload = it[0]
            if (payload != null) {
                itemBinding.tvNormalContent.text = payload.toString()
            }
        }
    }
}
```

## Rampage

加载状态？

```kotlin
//使用默认的加载状态
val dataSource = YashaDataSource(enableDefaultState = true)

//自定义加载状态
class CustomStateItem(val state: Int) : YashaItem
class CustomDataSource : YashaDataSource(enableDefaultState = false) {

    override fun onStateChanged(newState: Int) {
        setState(CustomStateItem(newState))
    }
}

//渲染自定义状态
recyclerView.linear(dataSource){
    ...
    renderBindingItem<CustomStateItem, CustomStateItemBinding> {
        onBind {
            when (data.state) {
                FetchingState.FETCHING -> {
                    //加载中
                }
                FetchingState.FETCHING_ERROR -> {
                    //加载失败
                }
                FetchingState.DONE_FETCHING -> {
                   //加载完成
                }
                else -> {
                    //其他
                }
            }
        }
    }
}
```

## 
    
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
