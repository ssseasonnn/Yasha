![](yasha.png)

[![](https://jitpack.io/v/ssseasonnn/Yasha.svg)](https://jitpack.io/#ssseasonnn/Yasha)

# Yasha 

*Read this in other languages: [中文](README.zh.md), [English](README.md)*

A lightweight library that quickly renders Recycler View.


> Item introduction:
>
> Yasha can be called the lightest weapon ever.
>
> Increase the agility by 16 points.
>
> Increase the attack power by 12 points.
>
> Increase the movement speed by 20 points.

> Yasha and Sange are good friends, they can synthesize more advanced weapons.
>
> ![](yasha.png) + ![](sange.png) = ![](yasha_and_sange.png)

## Prepare

1. Add jitpack to build.gradle
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add dependency

```gradle
dependencies {
    // Replace xyz with a specific version number, for example 1.0.0
	implementation 'com.github.ssseasonnn:Yasha:xyz'
}
```

## Start

Yasha is built on the basis of Sange. The function of Yasha core is also **DataSource** in Sange. 
Therefore, you need to know a little about Sange.

### First Blood

- Similarly, we have to define our data type first, this time to implement the **YashaItem** interface, for example

    ```kotlin
    class NormalItem(val number: Int): YashaItem
  
    class HeaderItem(val text:String): YashaItem
  
    class FooterItem(val text:String): YashaItem
    ```  
    
    As you can see, we no longer need to override the **viewType()** method to specify the item type!
    Yasha will automatically determine the type of each item!
    
- Next create your own DataSource.

    As shown below, we have inherited **YashaDataSource**

    ```kotlin
    class DemoDataSource : YashaDataSource() {
    
        override fun loadInitial(loadCallback: LoadCallback<YashaItem>) {
    
            //loadInitial will be called in the io thread, so there is no need to worry about any time-consuming operations
            Thread.sleep(2000)
    
            // loading
            val items = mutableListOf<YashaItem>()
            for (i in 0 until 10) {
                items.add(NormalItem(i))
            }
    
            //set result
            loadCallback.setResult(items)
        }
    
        override fun loadAfter(loadCallback: LoadCallback<YashaItem>) {
            //in the io thread
            Thread.sleep(2000)
    
            val items = mutableListOf<YashaItem>()
            for (i in page * 10 until (page + 1) * 10) {
                items.add(NormalItem(i))
            }
    
            loadCallback.setResult(items)
        }
    }
    
    ```
    
- Then, start rendering!

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
    
   As you can see, we don't have an Adapter, no ViewHolder! Yes, this is the power of the Yasha!

### Double Kill

By default, Yasha will show us a built-in loading status. If you want to change it, it's very convenient. Let's take a look. 

- Let's first create a data type that represents the state:

    ```kotlin
    class StateItem(val state: Int, val retry: () -> Unit) : YashaItem
    ```
  
- Similarly add our state Item in the DataSource:

    ```kotlin
    class DemoDataSource : YashaDataSource() {

        override fun loadInitial(loadCallback: LoadCallback<YashaItem>) {
            //...
        }

        override fun loadAfter(loadCallback: LoadCallback<YashaItem>) {
            //...
        }

        override fun onStateChanged(newState: Int) {
            //Add an extra state Item using the DataSource's setState method
            setState(StateItem(state = newState, retry = ::retry))
        }
    }
    ```
    
- Finally, render it! Remember, we no longer need an Adapter and no longer need ViewHolder!

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

- Refresh and retry

    Since the Yasha uses the DataSource of the Sange, the same is true, when the data needs to be refreshed, 
    the **invalidate()** method is called.
    When the load fails and needs to be retried, call the **retry()** method.

- Custom DiffCallback

    Like Sange, you can change the comparison logic according to your actual situation:

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
