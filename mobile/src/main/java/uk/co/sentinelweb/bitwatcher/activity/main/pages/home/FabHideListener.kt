package uk.co.sentinelweb.bitwatcher.activity.main.pages.home;

import android.animation.Animator
import android.animation.ObjectAnimator
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ScrollView

// TODO consider a coordinator layout
class FabHideListener(
        val scroll: ScrollView,
        val home_accounts_add_fab: FloatingActionButton)
    : ViewTreeObserver.OnScrollChangedListener {
    private var lastY: Int = 0;
    private var floatTop: Int = -1
    private var floatHeight: Int = -1
    private val ty = 500
    private lateinit var topDownAnimator: ObjectAnimator
    private lateinit var topUpAnimator: ObjectAnimator
    private lateinit var bottomDownAnimator: ObjectAnimator
    private lateinit var bottomUpAnimator: ObjectAnimator

    override fun onScrollChanged() {
        val scrollY = scroll.getScrollY() // For ScrollView
        if (floatTop == -1) {
            floatTop = home_accounts_add_fab.top
            floatHeight = home_accounts_add_fab.height
            makeAnimators()
        }
        if (scrollY > lastY) {
            topDownAnimator.start()
            bottomDownAnimator.start()
        } else if (scrollY < lastY) {
            topUpAnimator.start()
            bottomUpAnimator.start()
        }
        lastY = scrollY
    }

    private fun makeAnimators() {
        topDownAnimator = ObjectAnimator.ofInt(home_accounts_add_fab, "top", ty + floatTop)
        topDownAnimator.addListener(object : Animator.AnimatorListener {

            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                home_accounts_add_fab.visibility = View.GONE
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}

        })
        bottomDownAnimator = ObjectAnimator.ofInt(home_accounts_add_fab, "bottom", ty + floatTop + floatHeight)
        topUpAnimator = ObjectAnimator.ofInt(home_accounts_add_fab, "top", floatTop)
        topUpAnimator.addListener(object : Animator.AnimatorListener {

            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {
                home_accounts_add_fab.visibility = View.VISIBLE
            }

        })

        bottomUpAnimator = ObjectAnimator.ofInt(home_accounts_add_fab, "bottom", floatTop + floatHeight)

    }
}