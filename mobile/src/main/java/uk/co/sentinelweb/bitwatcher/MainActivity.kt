package uk.co.sentinelweb.bitwatcher

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.sentinelweb.bitwatcher.pages.home.HomeContract
import uk.co.sentinelweb.bitwatcher.pages.home.HomePresenter
import uk.co.sentinelweb.bitwatcher.pages.home.HomePresenterFactory

class MainActivity : AppCompatActivity() {
    var homePresenter:HomeContract.Presenter? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //message.setText(R.string.title_home)
                pager.setCurrentItem(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                pager.setCurrentItem(1)
                //message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                pager.setCurrentItem(2)
                //message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        pager.adapter = object : PagerAdapter() {
            override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
                val view:View
                when (position) {
                    0 -> {
                        homePresenter = HomePresenterFactory().createHomePresenter(this@MainActivity, container)
                        homePresenter?.loadData()
                        return homePresenter?.view()
                    }
                    1 -> {
                        val inflate = LayoutInflater.from(container?.context).inflate(R.layout.page_loops, container, false)
                        return inflate
                    }

                    2 -> {
                        val inflate = LayoutInflater.from(container?.context).inflate(R.layout.page_trade, container, false)
                        return inflate
                    }
                }

                return null

            }

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                //super.destroyItem(container, position, `object`)
            }

            override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return 3
            }
        }
    }


}
