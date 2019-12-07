package com.qiwu.cubepager

import android.content.Context
import com.qiwu.widget.cubepage.viewholder.CubeViewHolder

class DemoViewHolder(private val context: Context) : CubeViewHolder<DemoCubeView>() {

    var count = 0

    override fun instantiate(): DemoCubeView {
        val demoCubeView = DemoCubeView(context)
        count = (++count) % 3
        val id = when (count) {
                0 -> R.drawable.image_1
                1 -> R.drawable.image_2
                else -> R.drawable.image_3
            }
        demoCubeView.setImage(id)
        return demoCubeView
    }
}