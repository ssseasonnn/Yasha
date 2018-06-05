![](https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/yaksa.png)


# Yaksa

一个轻量级的RecyclerView工具，让你像Javascript一样渲染Item.

![](https://img.shields.io/badge/language-kotlin-brightgreen.svg)  

*Read this in other languages: [中文](README.ch.md), [English](README.md)* 

众所周知，熟练使用RecyclerView展示列表数据已经是每个Android开发者必备的能力，然而，RecyclerView仍然有它的不足，
那就是过于繁琐，相信每个开发者都有过这样的经历，我们为了展示一个只有单一类型的简单的列表，需要创建一个Adapter，
需要创建一个ViewHolder，而对于具有多个类型的稍微复杂的列表，我们不但需要创建Adapter，同时还要创建多个ViewHolder..

我们一次又一次不断的重复着这样无聊的工作，显得那么的麻木不仁

然而，生活不止眼前的苟且，还有诗和远方！

让我们一起和噩梦一样的Adapter和ViewHolder说声再见，一起来拥抱Yaksa吧!

> Yaksa(夜叉), 提高16点敏捷  15%攻击速度  10%移动速度


### 让我们来见识一下

```kotlin
recycler_view.linear {
    item {
        HeaderItem("This is a Header")
    }

    itemDsl(index = 0) {
        xml(R.layout.header_item)
        render {
            it.tv_header.text = "This is a dsl Header"
            it.setOnClickListener { toast("DSL Header Clicked") }
        }
    }

    data.forEach { each ->
        itemDsl {
            xml(R.layout.list_item)
            render {
                it.textView.text = each.title
            }
        }
    }
}
```

就是这样，没有Adapter,也没有ViewHolder，你只需要专心的渲染Item就好了！

效果图：

![](screenshot.png | width=200)

### License

> ```
> Copyright 2018 Season.Zlc
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