![](yasha.png)

[![](https://jitpack.io/v/ssseasonnn/Yasha.svg)](https://jitpack.io/#ssseasonnn/Yasha)

# Yasha 

*Read this in other languages: [中文](README.zh.md), [English](README.md), [Changelog](CHANGELOG.md)*

![](yasha_usage.png)

> Item introduction：
>
> Yasha is a DSL tool for fast rendering of RecyclerView based on [Sange](https://github.com/ssseasonnn/Sange).
> 
> Features:
> - No Adapter required
> - No ViewHolder required
> - Support for initial data loading
> - Support data paging loading
> - Support for MultiViewType
> - Support for Header and Footer
> - Support DiffUtil
> - Support Loading State
> - Support CleanUp, free resources to avoid memory leaks

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


## First Blood

- Rendering a Linear list: 

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

- Render Grid list:

    ```kotlin
      recycler_view.grid(dataSource) {
          //Set SpanCount
          spanCount(3)
  
          renderItem<HeaderItem> {
              res(R.layout.view_holder_header)
              onBind {
                  tv_header_content.text = data.toString()
              }
              
              //Set the SpanSize of the Item
              gridSpanSize(3)
          }
        
          renderItem<NormalItem> {
              res(R.layout.view_holder_normal)
              onBind {
                  tv_normal_content.text = data.toString()
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


## Triple Kill

- Render Grid list:

    ```kotlin
      recycler_view.stagger(dataSource) {
          //Set SpanCount
          spanCount(3)
        
          renderItem<HeaderItem> {
              res(R.layout.view_holder_header)
              onBind {
                  tv_header_content.text = data.toString()
              }
              
              //Set whether the item is full span
              staggerFullSpan(true)
          }
          renderItem<NormalItem> {
              res(R.layout.view_holder_normal)
              onBind {
                  tv_normal_content.text = data.toString()
              }
          }
          renderItem<FooterItem> {
              res(R.layout.view_holder_footer)
              onBind {
      }
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
