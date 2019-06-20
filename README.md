![](https://raw.githubusercontent.com/ssseasonnn/Yasha/master/yasha.png)


# Yasha


[![](https://jitpack.io/v/ssseasonnn/Yasha.svg)](https://jitpack.io/#ssseasonnn/Yasha)

### How to use

Step 1. Add the JitPack repository to your build file
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency

```gradle
dependencies {
    // replace xyz to latest version number
	implementation 'com.github.ssseasonnn:Yasha:xyz'
}
```

Step 3. Usage


```kotlin
recycler_view.linear(demoViewModel.dataSource) {
    renderItem<NormalItem> {
        res(R.layout.view_holder_normal)
        onBind {
            tv_normal_content.text = it.toString()
        }
    }
    renderItem<HeaderItem> {
        res(R.layout.view_holder_header)
        onBind {
            tv_header_content.text = it.toString()
        }
    }
    renderItem<FooterItem> {
        res(R.layout.view_holder_footer)
        onBind {
            tv_footer_content.text = it.toString()
        }
    }
}    
```


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