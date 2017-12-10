package uk.co.sentinelweb.bitwatcher.activity.main

import android.arch.lifecycle.LifecycleObserver
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.main.pages.PagesAdapter
import uk.co.sentinelweb.bitwatcher.app.BitwatcherApplication
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    lateinit var component: MainActivityComponent

    @Inject lateinit var presenter : MainContract.Presenter
    @Inject lateinit var pagesAdapter: PagesAdapter

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val newPosition = 0
                val title = R.string.title_home
                switchPage(newPosition, title)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_calcualator -> {
                switchPage(1, R.string.title_calculator)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                switchPage(2, R.string.title_loops)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                switchPage(3, R.string.title_trade)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun switchPage(newPosition: Int, @StringRes title: Int) {
        val oldPosition = main_pager.currentItem
        main_pager.setCurrentItem(newPosition)
        supportActionBar?.subtitle = getString(title)
        presenter.changePosition(newPosition, oldPosition)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportActionBar?.subtitle = getString(R.string.title_home)
        component = (application as BitwatcherApplication).component.mainActivityBuilder().mainActivity(this).build()
        component.inject(this)

        this.lifecycle.addObserver(presenter)

        main_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // disallow swiping of view pager to satisfy bottom nav ui pattern
        main_pager.setOnTouchListener({_,_ -> true})

        main_pager.adapter = pagesAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        this.lifecycle.removeObserver(presenter)
    }

    override fun registerLifecycleObserver(observer: LifecycleObserver) {
        lifecycle.addObserver(observer)
    }

    override fun unregisterLifecycleObserver(observer: LifecycleObserver) {
        this.lifecycle.removeObserver(observer)
    }



}
