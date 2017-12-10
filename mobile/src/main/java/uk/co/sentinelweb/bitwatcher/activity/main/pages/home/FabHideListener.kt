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
    companion object {
        private const val TY = 500
    }

    private var isAnimating = false
    private var isShown = true
    private var lastY: Int = 0;
    private var floatTop: Int = -1
    private var floatHeight: Int = -1
    private lateinit var topHideAnimator: ObjectAnimator
    private lateinit var topShowAnimator: ObjectAnimator
    private lateinit var bottomHideAnimator: ObjectAnimator
    private lateinit var bottomShowAnimator: ObjectAnimator

    override fun onScrollChanged() {
        val scrollY = scroll.getScrollY() // For ScrollView
        checkInitialised()
        if (scrollY > lastY) {
            hideFab()
        } else if (scrollY < lastY) {
            showFab()
        }
        lastY = scrollY
    }

    fun toggleFab() {
        checkInitialised()
        if (!isAnimating) {
            if (isShown) {
                hideFab()
            } else {
                showFab()
            }
        }
    }

    private fun checkInitialised() {
        if (floatTop == -1) {
            floatTop = home_accounts_add_fab.top
            floatHeight = home_accounts_add_fab.height
            makeAnimators()
        }
    }

    private fun showFab() {
        topShowAnimator.start()
        bottomShowAnimator.start()
        isShown = true
    }

    private fun hideFab() {
        topHideAnimator.start()
        bottomHideAnimator.start()
        isShown = false
    }

    private fun makeAnimators() {
        val topHidePosition = TY + floatTop
        val bottomHidePosition = topHidePosition + floatHeight
        topHideAnimator = ObjectAnimator.ofInt(home_accounts_add_fab, "top", topHidePosition)
        bottomHideAnimator = ObjectAnimator.ofInt(home_accounts_add_fab, "bottom", bottomHidePosition)
        topHideAnimator.addListener(object : Animator.AnimatorListener {

            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                home_accounts_add_fab.visibility = View.INVISIBLE
                isAnimating = false
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {isAnimating = true}

        })
        topShowAnimator = ObjectAnimator.ofInt(home_accounts_add_fab, "top", topHidePosition, floatTop)
        bottomShowAnimator = ObjectAnimator.ofInt(home_accounts_add_fab, "bottom", bottomHidePosition, floatTop + floatHeight)
        topShowAnimator.addListener(object : Animator.AnimatorListener {

            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {isAnimating = false}

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {
                home_accounts_add_fab.visibility = View.VISIBLE
                isAnimating = true
            }

        })

    }
}