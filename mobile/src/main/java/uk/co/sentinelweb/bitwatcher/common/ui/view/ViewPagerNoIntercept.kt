package uk.co.sentinelweb.bitwatcher.common.ui.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class ViewPagerNoIntercept : ViewPager {

    constructor(c: Context) : super(c)
    constructor(c: Context, attr: AttributeSet) : super(c, attr)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}