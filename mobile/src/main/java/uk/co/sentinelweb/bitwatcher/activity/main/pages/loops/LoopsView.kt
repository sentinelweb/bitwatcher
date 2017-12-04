package uk.co.sentinelweb.bitwatcher.activity.main.pages.loops

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.pages.loops.LoopsContract
import uk.co.sentinelweb.bitwatcher.activity.pages.loops.LoopsModel

class LoopsView(context: Context?): FrameLayout(context), LoopsContract.View {
    init {
        LayoutInflater.from(context).inflate(R.layout.main_loops_page, this, true)
    }

    override fun setData(model: LoopsModel) {

    }
}