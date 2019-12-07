package com.qiwu.cubepager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.qiwu.widget.cubepage.base.AbsCubeView

class DemoCubeView(context: Context) : AbsCubeView(context) {

    private val view: View =
        LayoutInflater.from(context).inflate(R.layout.layout_cube_demo_view, this, false)

    private val image: ImageView

    init {
        image = view.findViewById(R.id.image)
    }

    override fun getView(): View {
        return view
    }

    override fun onPreload() {
    }

    override fun onShowing() {
    }

    override fun onDismiss() {
    }

    override fun onScrollingChanged(scrolling: Boolean) {}

    fun setImage(sourceId : Int) {
        image.setImageResource(sourceId)
    }
}
