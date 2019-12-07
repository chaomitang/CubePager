package com.qiwu.cubepager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Scroller
import com.qiwu.widget.base.viewpager.SwipeDirection
import com.qiwu.widget.cubepage.view.CubeViewPager
import com.qiwu.widget.cubepage.viewholder.CubeViewHolder

class MainActivity : AppCompatActivity() {

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cubeViewPager: CubeViewPager<DemoCubeView> = findViewById(R.id.vp)

        val pageObserver = object : PageObserver {
            override fun getPageIndex(): Int {
                return cubeViewPager.currentIndex
            }
        }


        cubeViewPager.init(object : CubeViewHolder<DemoCubeView>(3) {
            override fun instantiate(): DemoCubeView {
                count = (++count) % this.pageNumber
                val v = DemoCubeView(this@MainActivity)
                v.setImage(getImageId(count))
                v.setPageObserver(pageObserver)
                return v
            }
        })

        cubeViewPager.setScroller(Scroller(this))
        cubeViewPager.setAllowedSwipeDirection(SwipeDirection.all)
    }

    fun getImageId(index: Int): Int {
        return when(index) {
            0 -> R.drawable.image_1
            1 -> R.drawable.image_2
            2 -> R.drawable.image_3
            else -> R.drawable.image_3
        }
    }
}
