package uk.co.sentinelweb.bitwatcher.activity.main

import android.arch.lifecycle.LifecycleObserver
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
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
                pager.setCurrentItem(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                pager.setCurrentItem(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                pager.setCurrentItem(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component = (application as BitwatcherApplication).component.mainActivityBuilder().mainActivity(this).build()
        component.inject(this)

        this.lifecycle.addObserver(presenter)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // disallow swiping of view pager to satisfy bottom nav ui pattern
        pager.setOnTouchListener({_,_ -> true})

        pager.adapter = pagesAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        this.lifecycle.removeObserver(presenter)
    }

    override fun unregisterLifecycleObserver(observer: LifecycleObserver) {
        this.lifecycle.removeObserver(observer)
    }



}
