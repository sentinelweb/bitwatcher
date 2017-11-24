package uk.co.sentinelweb.bitwatcher.activity.pages.loops

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import uk.co.sentinelweb.bitwatcher.R

class LoopsView(context: Context?): FrameLayout(context), LoopsContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.page_loops, this, true)
    }

    override fun setData(model: LoopsModel) {

    }
}