![](yasha.png)

[![](https://jitpack.io/v/ssseasonnn/Yasha.svg)](https://jitpack.io/#ssseasonnn/Yasha)

*Read this in other languages: [中文](README.zh.md), [English](README.md), [Changelog](CHANGELOG.md)*

# Yasha (夜叉)

![](yasha_usage.png)

> 物品介绍：
>
> 基于Kotlin的现代化RecyclerView渲染武器
> 
> 物品特点:
> - 无需Adapter
> - 无需ViewHolder
> - 支持初始化数据加载
> - 支持数据分页加载
> - 支持MultiViewType
> - 支持Header和Footer
> - 支持DiffUtil
> - 支持Loading State
> - 支持CleanUp, 释放资源避免内存泄漏

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
	implementation 'com.github.ssseasonnn:Yasha:1.0.8'
}
```


## First Blood

如何快速渲染一个RecyclerView？还在不厌其烦的写Adapter,ViewHolder? 快让夜叉来拯救你吧：

   ```kotlin

     //以 **dataSource** 作为数据源渲染一个列表
     recycler_view.linear(dataSource) {

         //渲染一个NormalItem类型的Item
         renderItem<NormalItem> {
             //布局文件
             res(R.layout.view_holder_normal)

             //绑定数据 
             onBind {
                 tv_normal_content.text = data.toString()
             }
         }

         //渲染一个HeaderItem类型的头布局
         renderItem<HeaderItem> {
             //布局文件
             res(R.layout.view_holder_header)

             //绑定数据
             onBind {
                 tv_header_content.text = data.toString()
             }
         }

         //渲染一个FooterItem类型的尾部
         renderItem<FooterItem> {

             //布局文件
             res(R.layout.view_holder_footer)

             //绑定数据
             onBind {
                 tv_footer_content.text = data.toString()
             }
         }
     }
   ```

如你所见，非常简单并且直观，并且没有看到任何的Adapter以及ViewHolder，装备夜叉，去Gank吧！

## Double Kill

渲染搞定了，数据源从何而来呢？夜叉贴心的为你准备了它的好伙伴：[散华](https://github.com/ssseasonnn/Sange)

散华是另一件强大的武器，它能提供夜叉所需要的任何数据，有了它夜叉才能如鱼得水，就像在Dota中它俩也是形影不离，熟悉Dota的朋友都知道，夜叉+散华=暗夜对剑，所以这里也是一样，有了它们俩，何惧任何逆风局？

```kotlin
//创建一个DataSource
class DemoDataSource : YashaDataSource() {
   
    //loadInitial负责加载列表初始化数据
    override fun loadInitial(loadCallback: LoadCallback<YashaItem>) {
        
        //模拟延时，不用担心，该方法会在异步线程中执行，因此你可以放心在这里进行网络请求或者数据库加载等等
        Thread.sleep(1500)

        val allDataList = mutableListOf<YashaItem>()

        //添加Header数据
        for (i in 0 until 2) {
            allDataList.add(HeaderItem(i))
        }
        //添加Item数据
        for (i in 0 until 10) {
            allDataList.add(NormalItem(i))
        }
        //添加Footer数据
        for (i in 0 until 2) {
            allDataList.add(FooterItem(i))
        }

        //通知数据加载完毕
        loadCallback.setResult(allDataList)
    }

    //loadAfter负责加载下一页数据，夜叉会自己决定是否触发了分页加载，你只需要做好加载下一页的逻辑，其他的交给夜叉吧
    override fun loadAfter(loadCallback: LoadCallback<YashaItem>) {
       
        //当加载失败：
        if (page % 3 == 0) {

            //当数据加载失败时，只需要简单告诉夜叉一个NULL值，夜叉就会自动停止加载，并显示一个加载失败的状态
            loadCallback.setResult(null)
            return
        }

        //当加载完毕：
        if(page == 5) {
            //当数据加载完之后，只需要告诉夜叉一个空列表，夜叉就会自动停止加载，并且显示一个加载完毕的状态
            loadCallback.setResult(emptyList())
            return
        }

        //加载下一页数据：
        val items = mutableListOf<YashaItem>()
        for (i in page * 10 until (page + 1) * 10) {
            items.add(NormalItem(i))
        }

        loadCallback.setResult(items)
    }
}
```

是不是很轻松？没错，夜叉和散华提供的API简单易懂，无需任何门槛即可快速上手，简直是居家旅行，杀人越货必备的武器！

除此之外，散华还提供了很多直接操作数据源的API，例如

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

//Items
fun addItem(t: T, position: Int = -1, delay: Boolean = false)
fun addItems(list: List<T>, position: Int = -1, delay: Boolean = false) 
fun removeItem(t: T, delay: Boolean = false) 
fun setItem(old: T, new: T, delay: Boolean = false)
fun getItem(position: Int): T
fun clearItem(delay: Boolean = false)

//等等API接口
```

## Triple Kill

除此之外，夜叉还贴心的为各位小哥们准备了状态的显示，如常用的**加载中**，**加载失败**，**加载完成** 等

那如果对自带的状态有任何不满，那很简单，第一种办法就是干掉它，第二种办法就是替换它

```kotlin
//干掉它：
class DemoDataSource : YashaDataSource() {

    override fun onStateChanged(newState: Int) {
        //super.onStateChanged(newState)
        //只需要重新这个方法并且注释掉super的调用即可，这样就没有任何状态显示啦！
    }
}

//替换它：
recycler_view.linear(dataSource) {

    //替换成你自己的状态渲染方式
    renderItem<YashaStateItem> {
        //新的状态布局
        res(R.layout.your_state_view)

        //新的状态绑定
        onBind {
            
        }
        gridSpanSize(spanCount)
        staggerFullSpan(true)
    }
}
```

## 天辉获胜，GG

    
## License

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
