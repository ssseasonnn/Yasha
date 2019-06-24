![](https://raw.githubusercontent.com/ssseasonnn/Yasha/master/yasha.png)

[![](https://jitpack.io/v/ssseasonnn/Yasha.svg)](https://jitpack.io/#ssseasonnn/Yasha)

# Yasha (夜叉)

*Read this in other languages: [中文](README.zh.md), [English](README.md)*

一个快速渲染RecyclerView的轻量级库.


> 物品介绍：
>
> 散华是一件异常精准的武器。它具有不可思议的灵性，就好像它会自己寻找对手的弱点进行攻击。
>
> 增加16点的力量。
>
> 增加10点的攻击力。
>
> 残废（被动）：在攻击中有15%的几率使目标残废。残废效果降低目标20%的移动速度，持续4秒。

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

### First Blood

- 散华核心的功能是**DataSource**, 利用它,只需几个简单的步骤,即可轻松实现数据的初始化及分页加载.

    在此之前, 我们得先定义好我们的数据类型,记得实现**SangeItem**接口 例如:
    
    ```kotlin
    class NormalItem(val number: Int): SangeItem
    ```  

- 接下来创建你自己的DataSource.

    如下所示,我们继承了**MultiDataSource**, 并把**SangeItem**当作泛型参数, 然后实现**loadInitial**和**loadAfter**方法:

    ```kotlin
    class DemoDataSource : MultiDataSource<SangeItem>() {
    
        override fun loadInitial(loadCallback: LoadCallback<SangeItem>) {
    
            //loadInitial 将会在子线程中调用, 因此无需担心任何耗时操作
            Thread.sleep(2000)
    
            // 加载数据
            val items = mutableListOf<SangeItem>()
            for (i in 0 until 10) {
                items.add(NormalItem(i))
            }
    
            //将加载之后的数据传递给 LoadCallback, 即可轻松更新RecyclerView
            loadCallback.setResult(items)
        }
    
        override fun loadAfter(loadCallback: LoadCallback<SangeItem>) {
            //loadAfter 将会在子线程中调用, 因此无需担心任何耗时操作
            Thread.sleep(2000)
    
            val items = mutableListOf<SangeItem>()
            for (i in page * 10 until (page + 1) * 10) {
                items.add(NormalItem(i))
            }
    
            loadCallback.setResult(items)
        }
    }
    
    ```

    loadInitial和loadAfter方法都将在子线程中调用, 因此无需担心在这两个方法中做的任何耗时操作.

    数据加载完成后, 只需调用LoadCallback的setResult(list)方法即可, 散华会替你做好其他的一切工作,
    包括线程切换,通知界面更新等, 你需要做的, 仅仅只是关注于数据的加载.
    
- 接下来创建一个用于展示的ViewHolder吧, 通过继承散华提供的**SangeViewHolder**, 你可以省略很多其他繁琐的工作.

    例如:

    ```kotlin
    class NormalViewHolder(containerView: View) :
            SangeViewHolder<SangeItem>(containerView) {

        override fun onBind(t: SangeItem) {
            t as NormalItem
            tv_normal_content.text = t.toString()
        }
    }
    ```

- 下一步就是创建一个你自己的Adapter,通过继承散华提供的**SangeMultiAdapter**, 你可以轻松的将DataSource结合起来.

    例如:

    ```kotlin
    class DemoAdapter(dataSource: DataSource<SangeItem>) :
            SangeMultiAdapter<SangeItem, SangeViewHolder<SangeItem>>(dataSource) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SangeViewHolder<SangeItem> {
            return NormalViewHolder(inflate(parent, R.layout.view_holder_normal))
        }
    }
    ```

- 最后, 将RecyclerView和Adapter关联起来:

    ```kotlin
    recycler_view.layoutManager = LinearLayoutManager(this)
    recycler_view.adapter = DemoAdapter(DemoDataSource())
    ```

    就是这样, 你无需关心分页的逻辑, 你只需要专注于你真正应该关注的东西: 加载数据, 其他的就交给散华吧!

### Double Kill

到目前为止我们一切进展很顺利, 可是似乎缺少了分页加载的状态显示, 下面来实现它吧.

- 为了显示加载的状态,我们先创建一个表示状态的数据类型:

    ```kotlin
    class StateItem(val state: Int, val retry: () -> Unit) : SangeItem {
        override fun viewType() = STATE
    }
    ```
    > 如你所见, 我们同样实现了**SangeItem**接口, 并且实现了viewType方法, 在该方法中返回了一个新的Type类型

- 接着我们稍微改造一下DataSource,我们实现一个额外的方法: **onStateChanged(newState)**.

    ```kotlin
    class DemoDataSource : MultiDataSource<SangeItem>() {

        override fun loadInitial(loadCallback: LoadCallback<SangeItem>) {
            //...
        }

        override fun loadAfter(loadCallback: LoadCallback<SangeItem>) {
            //...
        }

        override fun onStateChanged(newState: Int) {
            //利用DataSource的setState方法, 添加一个额外的状态Item
            setState(StateItem(state = newState, retry = ::retry))
        }
    }
    ```

    这个方法会在分页加载的不同阶段来调用,用来告诉我们目前DataSource的状态, 例如加载中, 加载失败, 加载成功等.
    通过实现这个方法, 我们便可以自行控制加载状态的显示与否, 以及对显示的样式进行定制.

    如上所示, 我们添加了一个用来表示状态的数据Item项.

- 同样的, 我们需要一个渲染State的ViewHolder:

    ```kotlin
    class StateViewHolder(containerView: View) :
            SangeViewHolder<SangeItem>(containerView) {

        override fun onBind(t: SangeItem) {
            super.onBind(t)
            t as StateItem

            tv_state_content.setOnClickListener {
                t.retry()
            }

            when {
                t.state == FetchingState.FETCHING -> {
                    state_loading.visibility = View.VISIBLE
                    tv_state_content.visibility = View.GONE
                }
                t.state == FetchingState.FETCHING_ERROR -> {
                    state_loading.visibility = View.GONE
                    tv_state_content.visibility = View.VISIBLE
                }
                t.state == FetchingState.DONE_FETCHING -> {
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
    ```

- 最后,显示加载状态

    ```kotlin
    class DemoAdapter(dataSource: DataSource<SangeItem>) :
            SangeMultiAdapter<SangeItem, SangeViewHolder<SangeItem>>(dataSource) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SangeViewHolder<SangeItem> {
            return when (viewType) {
                STATE -> StateViewHolder(inflate(parent, R.layout.view_holder_state))
                else -> NormalViewHolder(inflate(parent, R.layout.view_holder_normal))
            }
        }
    }


    ```

### Triple Kill

- 刷新与重试

    散华的DataSource提供了 **invalidate()** 和 **retry()** 方法, 当需要刷新数据时, 调用 **invalidate()** 方法即可,
    当加载失败需要重试时, 调用 **retry()** 方法即可

- 定制DiffCallback

    散华使用了DiffUtil来高效的更新RecyclerView,你可以根据你的实际情况来改变比较逻辑:

    ```kotlin
    class NormalItem(val i: Int) : SangeItem {

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
