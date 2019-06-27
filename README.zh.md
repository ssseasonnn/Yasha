![](yasha.png)

[![](https://jitpack.io/v/ssseasonnn/Yasha.svg)](https://jitpack.io/#ssseasonnn/Yasha)

# Yasha (夜叉)

*Read this in other languages: [中文](README.zh.md), [English](README.md)*

一个快速渲染RecyclerView的轻量级库.


> 物品介绍：
>
> 夜叉可以称得上有史以来最轻巧的武器。
>
> 增加16点的敏捷。
>
> 增加12点的攻击力。
>
> 增加20点的移动速度。

> 夜叉和散华是好朋友, 它们可以合成更高级的武器 -- 散夜对剑
>
> ![](yasha.png) + ![](sange.png) = ![](yasha_and_sange.png)

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
    // 替换 xyz 为具体的版本号, 例如 1.0.0
	implementation 'com.github.ssseasonnn:Yasha:xyz'
}
```

## Start

夜叉是在散华的基础上打造而成, 夜叉核心的功能同样是散华中的**DataSource**,因此,你需要对散华进行一定的了解.

### First Blood

- 同样的,我们得先定义好我们的数据类型, 这次要实现**YashaItem**接口, 例如

    ```kotlin
    class NormalItem(val number: Int): YashaItem
  
    class HeaderItem(val text:String): YashaItem
  
    class FooterItem(val text:String): YashaItem
    ```  
    
    如你所见, 我们不再需要重写 **viewType()** 方法来指定item类型了! 夜叉会自动判断每种item的类型!
    
- 接下来创建你自己的DataSource.

    如下所示,我们继承了**YashaDataSource**

    ```kotlin
    class DemoDataSource : YashaDataSource() {
    
        override fun loadInitial(loadCallback: LoadCallback<YashaItem>) {
    
            //loadInitial 将会在子线程中调用, 因此无需担心任何耗时操作
            Thread.sleep(2000)
    
            // 加载数据
            val items = mutableListOf<YashaItem>()
            for (i in 0 until 10) {
                items.add(NormalItem(i))
            }
    
            //将加载之后的数据传递给 LoadCallback, 即可轻松更新RecyclerView
            loadCallback.setResult(items)
        }
    
        override fun loadAfter(loadCallback: LoadCallback<YashaItem>) {
            //loadAfter 将会在子线程中调用, 因此无需担心任何耗时操作
            Thread.sleep(2000)
    
            val items = mutableListOf<YashaItem>()
            for (i in page * 10 until (page + 1) * 10) {
                items.add(NormalItem(i))
            }
    
            loadCallback.setResult(items)
        }
    }
    
    ```
    
- 然后, 开始渲染吧!

    ```kotlin
   recycler_view.linear(dataSource) {
       renderItem<NormalItem> {
           res(R.layout.view_holder_normal)
           onBind {
               tv_normal_content.text = data.toString()
           }
       }
       renderItem<HeaderItem> {
           res(R.layout.view_holder_header)
           onBind {
               tv_header_content.text = data.toString()
           }
       }
       renderItem<FooterItem> {
           res(R.layout.view_holder_footer)
           onBind {
               tv_footer_content.text = data.toString()
           }
       }
   }
    ```
    
   如你所见, 我们没有Adapter, 没有ViewHolder! 是的,这就是夜叉的强大威力!

### Double Kill

默认情况下, 夜叉会为我们显示一个内置的加载状态, 如果你想更改它,也很方便,来看一下吧. 

- 我们先创建一个表示状态的数据类型:

    ```kotlin
    class StateItem(val state: Int, val retry: () -> Unit) : YashaItem
    ```
  
- 同样的在DataSource中添加我们的状态Item:

    ```kotlin
    class DemoDataSource : YashaDataSource() {

        override fun loadInitial(loadCallback: LoadCallback<YashaItem>) {
            //...
        }

        override fun loadAfter(loadCallback: LoadCallback<YashaItem>) {
            //...
        }

        override fun onStateChanged(newState: Int) {
            //利用DataSource的setState方法, 添加一个额外的状态Item
            setState(StateItem(state = newState, retry = ::retry))
        }
    }
    ```
    
- 最后, 渲染它吧! 记住, 我们不再需要Adapter,不再需要ViewHolder!

    ```kotlin
    recycler_view.linear(dataSource) {
        
        renderItem<StateItem> {
            res(R.layout.view_holder_state)
            onBind {
                tv_state_content.setOnClickListener {
                    data.retry()
                }
                when {
                    data.state == FetchingState.FETCHING -> {
                        state_loading.visibility = View.VISIBLE
                        tv_state_content.visibility = View.GONE
                    }
                    data.state == FetchingState.FETCHING_ERROR -> {
                        state_loading.visibility = View.GONE
                        tv_state_content.visibility = View.VISIBLE
                    }
                    data.state == FetchingState.DONE_FETCHING -> {
                        state_loading.visibility = View.GONE
                        tv_state_content.visibility = View.GONE
                    }
                    else -> {
                        state_loading.visibility = View.GONE
                        tv_state_content.visibility = View.GONE
                    }
                }
            }
        }
    }
    ```

### Triple Kill

- 刷新与重试

    由于夜叉使用了散华的DataSource, 因此同样的, 当需要刷新数据时, 调用 **invalidate()** 方法即可,
    当加载失败需要重试时, 调用 **retry()** 方法即可

- 定制DiffCallback

    和散华一样, 你可以根据你的实际情况来改变比较逻辑:

    ```kotlin
    class NormalItem(val i: Int) : YashaItem {

        override fun areContentsTheSame(other: Differ): Boolean {
            //use your own diff logic
            //...
        }

        override fun areItemsTheSame(other: Differ): Boolean {
            //use your own diff logic
            //...
        }

        override fun getChangePayload(other: Differ): Any? {
            //...
        }
    }
    ```

## END
    
![](https://github.com/ssseasonnn/Sange/raw/master/multi.gif)
    

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
