![](yasha.png)

[![](https://jitpack.io/v/ssseasonnn/Yasha.svg)](https://jitpack.io/#ssseasonnn/Yasha)

# Yasha (夜叉)

*Read this in other languages: [中文](README.zh.md), [English](README.md), [Changelog](CHANGELOG.md)*

![](yasha_usage.png)

> 物品介绍：
>
> 夜叉是一个基于[散华](https://github.com/ssseasonnn/Sange)打造的快速渲染RecyclerView的DSL工具.
> 
> 功能介绍:
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
    // 替换 xyz 为具体的版本号, 例如 1.0.0
	implementation 'com.github.ssseasonnn:Yasha:xyz'
}
```


## First Blood

- 渲染Linear列表: 

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

- 渲染Grid列表:

    ```kotlin
      recycler_view.grid(dataSource) {
          //设置SpanCount
          spanCount(3)
  
          renderItem<HeaderItem> {
              res(R.layout.view_holder_header)
              onBind {
                  tv_header_content.text = data.toString()
              }
              
              //设置Item的SpanSize
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

- 渲染Grid列表:

    ```kotlin
      recycler_view.stagger(dataSource) {
          //设置SpanCount
          spanCount(3)
        
          renderItem<HeaderItem> {
              res(R.layout.view_holder_header)
              onBind {
                  tv_header_content.text = data.toString()
              }
              
              //设置Item是否为full span
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
