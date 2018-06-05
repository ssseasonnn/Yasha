![](https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/yaksa.png)


# Yaksa

A lightweight RecyclerView tool that lets you render items like Javascript.

![](https://img.shields.io/badge/language-kotlin-brightgreen.svg)  

*Read this in other languages: [中文](README.ch.md), [English](README.md)* 

As we all know, using RecyclerView to display list data is a must-have feature for every Android developer.

However, RecyclerView still has its shortcomings, that is too cumbersome.

I believe every developer has had this experience: 

To show a simple list of only a single type, we need to create an Adapter and we need to create a ViewHolder,

for a slightly more complicated list with multiple types, we need not only to create an Adapter, but also to create multiple ViewHolders

We repeated such boring work again and again and again, and it seemed so insensitive.

However, there is more to life than just present, poetry and distance!

Let's say goodbye to the same Adapter and ViewHolder as the nightmare, and embrace Yaksa together!

> Yaksa(夜叉), Increases 16 Agility 15% Attack Speed ​​10% Movement Speed


### Let us come to see

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

That's it, there is no Adapter, no ViewHolder, you just need to concentrate on rendering the Item just fine!

Output:

<img src="https://raw.githubusercontent.com/ssseasonnn/Yaksa/master/screenshot.png" width="300">


### Add to your project build.gradle file:

```groovy
dependencies {
	implementation 'zlc.season:yaksa:x.x.x'
}
```

(Please replace x with the latest version numbers: [![Download](https://api.bintray.com/packages/ssseasonnn/android/Yaksa/images/download.svg)](https://bintray.com/ssseasonnn/android/Yaksa/_latestVersion))

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