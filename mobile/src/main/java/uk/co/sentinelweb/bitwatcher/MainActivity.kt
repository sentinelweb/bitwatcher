package uk.co.sentinelweb.bitwatcher

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.sentinelweb.bitwatcher.pages.PagerFactory

class MainActivity : AppCompatActivity(), MainContract.View {
    private lateinit var presenter: MainContract.Presenter
    private val pagerFactory = PagerFactory()

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

    override fun onStart() {
        super.onStart()
        presenter.onStart()// TODO use lifecycle componets
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()// TODO use lifecycle componets
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this)
        presenter.onCreate()// TODO use lifecycle componets
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        pager.adapter = object : PagerAdapter() {
            override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
                val pagePresenter = pagerFactory.createPagePresenter(container, position)
                presenter.addPagePresenter(position, pagePresenter)
                Log.d("MainActivity", "add item ${position}")
                return pagePresenter.view()
            }

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                Log.d("MainActivity", "remove item ${position}")
                val removePagePresenter = presenter.removePagePresenter(position)
                container?.removeView(removePagePresenter?.view())
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
