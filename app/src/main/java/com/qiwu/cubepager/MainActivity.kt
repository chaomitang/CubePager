package com.qiwu.cubepager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Scroller
import com.qiwu.widget.base.viewpager.SwipeDirection
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vp.init(DemoViewHolder(this))
        vp.setScroller(Scroller(this))
        vp.setAllowedSwipeDirection(SwipeDirection.all)
    }
}
