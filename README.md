# CubePager
A cube effect sliding UI widget which can be set infinite-scroll mode, with elastic rebound。


### Use like this below
```kotlin
val cubeViewPager: CubeViewPager<DemoCubeView> = findViewById(R.id.vp)
cubeViewPager.init(object : CubeViewHolder<DemoCubeView>() {
    override fun instantiate(): DemoCubeView {
        return DemoCubeView(this@MainActivity)
    }
})
```

### Also provide ```CubeFragmentPager``` for fragments

