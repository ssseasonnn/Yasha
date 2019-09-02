![](yasha.png)

[![](https://jitpack.io/v/ssseasonnn/Yasha.svg)](https://jitpack.io/#ssseasonnn/Yasha)

# Yasha (夜叉)

*Read this in other languages: [中文](README.zh.md), [English](README.md), [Changelog](CHANGELOG.md)*

![](yasha_usage.png)

## Prepare

1. Add jitpack repo:
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
	implementation 'com.github.ssseasonnn:Yasha:xyz'
}
```


## First Blood

- Quick render: 

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

## Double Kill

- Custom DataSource:

    ```kotlin
    class DemoDataSource : YashaDataSource() {
        
        override fun loadInitial(loadCallback: LoadCallback<YashaItem>) {
            // Load initial data
            val items = mutableListOf<YashaItem>()
            for (i in 0 until 10) {
                items.add(NormalItem(i))
            }
            //set result to dataSource, 
            loadCallback.setResult(items)
        }
        override fun loadAfter(loadCallback: LoadCallback<YashaItem>) {
            // Load next page data
            val items = mutableListOf<YashaItem>()
            for (i in page * 10 until (page + 1) * 10) {
                items.add(NormalItem(i))
            }
            loadCallback.setResult(items)
        }
    }
    ```

- Custom load state:

    ```kotlin
    class StateItem(val state: Int, val retry: () -> Unit) : YashaItem
    ```
  
    ```kotlin
    class DemoDataSource : YashaDataSource() {

        override fun loadInitial(loadCallback: LoadCallback<YashaItem>) {
            //...
        }

        override fun loadAfter(loadCallback: LoadCallback<YashaItem>) {
            //...
        }

        override fun onStateChanged(newState: Int) {
            //set our state item
            setState(StateItem(state = newState, retry = ::retry))
        }
    }
    ```
    
    ```kotlin
    recycler_view.linear(dataSource) {
        
        renderItem<StateItem> {
            res(R.layout.view_holder_state)
            onBind {
                tv_state_content.setOnClickListener {
                    data.retry()
                }
            }
        }
    }
    ```

## Triple Kill

- Refresh:

    ```kotlin
    dataSource.invalidate()    
    ```
    
- Retry:

    ```kotlin
    dataSource.retry()
    ```
    
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
