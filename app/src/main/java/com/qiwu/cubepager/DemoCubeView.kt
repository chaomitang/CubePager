package com.qiwu.cubepager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qiwu.widget.cubepage.base.AbsCubeView

class DemoCubeView(context: Context) : AbsCubeView(context) {

    private val view: View =
        LayoutInflater.from(context).inflate(R.layout.layout_cube_demo_view, this, false)

    private val image: ImageView
    private val textView: TextView
    private var pageObserver: PageObserver? = null

    init {
        image = view.findViewById(R.id.image)
        textView = view.findViewById(R.id.tv_page_index)
    }

    override fun getView(): View {
        return view
    }

    override fun onPreload() {
    }

    override fun onShowing() {
        textView.text = "${pageObserver?.getPageIndex() ?: 0}"
    }

    override fun onDismiss() {
    }

    override fun onScrollingChanged(scrolling: Boolean) {}


    fun setPageObserver(observer: PageObserver) {
        pageObserver = observer
    }
    fun setImage(sourceId : Int) {
        image.setImageResource(sourceId)
    }
}
