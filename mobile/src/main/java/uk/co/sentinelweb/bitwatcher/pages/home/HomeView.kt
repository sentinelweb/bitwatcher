package uk.co.sentinelweb.bitwatcher.pages.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.page_home.view.*
import uk.co.sentinelweb.bitwatcher.R

class HomeView( context: Context?) : FrameLayout(context), HomeContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.page_home, this, true)
    }

    override fun setData(model: HomeModel) {
        price_text.text = model.price_text
    }

}