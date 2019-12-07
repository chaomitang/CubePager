# CubePager
A cube effect sliding UI widget which can be set infinite-scroll mode, with elastic rebound。


---

![image](https://github.com/chaomitang/CubePager/cubepager.gif)

### 1. Use like this below
```kotlin
val cubeViewPager: CubeViewPager<DemoCubeView> = findViewById(R.id.vp)
cubeViewPager.init(object : CubeViewHolder<DemoCubeView>(3) { // 3 means page number
    override fun instantiate(): DemoCubeView {
        return DemoCubeView(this@MainActivity)
    }
})
```

### 2. Also provide ```CubeFragmentPager``` for fragments


### 3. If you want you control scroll speed, set Scroller

```kotlin
cubeViewPager.setScroller(Scroller(this))
```

### 4. You can control if forbid the swipe direction left, right, none, all.
```kotlin
cubeViewPager.setAllowedSwipeDirection(SwipeDirection.all)
cubeViewPager.setAllowedSwipeDirection(SwipeDirection.none)
cubeViewPager.setAllowedSwipeDirection(SwipeDirection.left)
cubeViewPager.setAllowedSwipeDirection(SwipeDirection.right)
```
if you set like this :
```kotlin
cubeViewPager.setAllowedSwipeDirection(SwipeDirection.right)
```
![image](https://github.com/chaomitang/CubePager/cubepager_forbidden_toleft.gif)

