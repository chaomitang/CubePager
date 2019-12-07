package com.qiwu.cubepager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qiwu.widget.cubepage.view.CubeViewPager
import com.qiwu.widget.cubepage.viewholder.CubeViewHolder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cubeViewPager: CubeViewPager<DemoCubeView> = findViewById(R.id.vp)
        cubeViewPager.init(object : CubeViewHolder<DemoCubeView>() {
            override fun instantiate(): DemoCubeView {
                return DemoCubeView(this@MainActivity)
            }
        })
    }
}
