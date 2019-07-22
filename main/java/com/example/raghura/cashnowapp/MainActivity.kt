package com.example.raghura.cashnowapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyOffersListFragment.OnOfferSelectedListener  , AcceptedOffersListFragment.OnOfferSelectedListenerAccepted , AvailableOffersListFragment.OnOfferSelectedListener {
    override fun onOfferSelected(offer: Offer) {

       Toast.makeText(this, offer.toString(), Toast.LENGTH_LONG).show()
        val offerFragment = OfferDetailFragment.newInstance(offer)
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, offerFragment)
        ft.addToBackStack("detail")
        ft.commit()
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var switchTo : Fragment? = null
        when (item.itemId) {
            R.id.navigation_my_offers -> {
                toolbar.title = "My Offers"
                Toast.makeText(this,"Changed", Toast.LENGTH_LONG).show()
               switchTo = MyOffersListFragment()
                if  ( switchTo != null) {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment_container, switchTo)
                    while (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStackImmediate()
                    }
                    ft.commit()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_available_offers -> {
                toolbar.title = "Available Offers"
                switchTo = AvailableOffersListFragment()
                if  ( switchTo != null) {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment_container, switchTo)
                    while (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStackImmediate()
                    }
                    ft.commit()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_accepted_offers -> {
                toolbar.title = "Accepted Offers"
                switchTo = AcceptedOffersListFragment()
                if  ( switchTo != null) {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment_container, switchTo)
                    while (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStackImmediate()
                    }
                    ft.commit()
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_new_offers -> {
                toolbar.title = "New Offer"
                switchTo = NewOfferFragment()
                if  ( switchTo != null) {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment_container, switchTo)
                    while (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStackImmediate()
                    }
                    ft.commit()
                }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.title = "Available Offers"
        setSupportActionBar(toolbar)
//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        var switchTo : Fragment? = null

        switchTo = AvailableOffersListFragment()
        if  ( switchTo != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, switchTo)
            while (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStackImmediate()
            }
            ft.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings ->{
                toolbar.title ="Settings"
                var switchTo : Fragment? = null

                switchTo = SettingsFragment()
                if  ( switchTo != null) {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment_container, switchTo)
                    while (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStackImmediate()
                    }
                    ft.commit()
                }
                true}

            else -> super.onOptionsItemSelected(item)
        }
    }
}
