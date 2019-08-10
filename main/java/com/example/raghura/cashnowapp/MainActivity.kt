package com.example.raghura.cashnowapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add.view.*
import kotlinx.android.synthetic.main.dialog_filter.view.*

class MainActivity : AppCompatActivity(),SplashFragment.OnLoginButtonPressedListener, MyOffersListFragment.OnOfferSelectedListener  , AcceptedOffersListFragment.OnOfferSelectedListenerAccepted , AvailableOffersListFragment.OnOfferSelectedListener {
    var Fbool : Boolean = false

    //authentication stuff
    private val auth = FirebaseAuth.getInstance()
    lateinit var authStateListener : FirebaseAuth.AuthStateListener
    private val RC_SIGN_IN = 1
    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener (authStateListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener { authStateListener }
    }
    lateinit var Guser : FirebaseUser
    private fun initializeListeners() {
        // TODO: Create an AuthStateListener that passes the UID
        // to the MovieQuoteFragment if the user is logged in
        // and goes back to the Splash fragment otherwise.
        // See https://firebase.google.com/docs/auth/users#the_user_lifecycle
        authStateListener = FirebaseAuth.AuthStateListener { auth :FirebaseAuth ->
            val user = auth.currentUser

            Log.d(Constants.TAG, "In auth Listener, user = $user")
            if(user != null) {
                Guser= user!!
                Log.d(Constants.TAG,"UID: ${user.uid}")
                Log.d(Constants.TAG,"Name: ${user.displayName}")
                Log.d(Constants.TAG,"Email: ${user.email}")
                Log.d(Constants.TAG,"Phone: ${user.phoneNumber}")
                Log.d(Constants.TAG,"Photo: ${user.photoUrl}")
                //switchToMovieQuoteFragment(user.uid)
                Toast.makeText(this, "made it ", Toast.LENGTH_LONG)
//                var switchTo : Fragment? = null
//                switchTo = PicFragment()
//
//                if  ( switchTo != null) {
//                    val ft = supportFragmentManager.beginTransaction()
//                    ft.replace(R.id.fragment_container, switchTo)
//                    while (supportFragmentManager.backStackEntryCount > 0) {
//                        supportFragmentManager.popBackStackImmediate()
//                    }
//                    ft.commitAllowingStateLoss()
//                }
            //    val ft = supportFragmentManager.beginTransaction()
              //  ft.replace(R.id.fragment_container, PicFragment.newInstance(user.uid , false ))
//                var switchTo : Fragment? = null
//
//                switchTo = AvailableOffersListFragment()
//                if  ( switchTo != null) {
//                    val ft = supportFragmentManager.beginTransaction()
//                    ft.replace(R.id.fragment_container, switchTo)
//                    while (supportFragmentManager.backStackEntryCount > 0) {
//                        supportFragmentManager.popBackStackImmediate()
//                    }
//                    ft.commitAllowingStateLoss()
//                }
//              //  ft.commitAllowingStateLoss()

                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragment_container, AvailableOffersListFragment.newInstance(user.uid , false , Guser))
                ft.commitAllowingStateLoss()

            } else {
                switchToSplashFragment()
            }
        }


    }
    private fun switchToSplashFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, SplashFragment())
        ft.commit()
    }
    override fun onLoginButtonPressed() {
        launchLoginUI()
    }
    private fun launchLoginUI() {
        // TODO: Build a login intent and startActivityForResult(intent, ...)
        // For details, see https://firebase.google.com/docs/auth/android/firebaseui#sign_in
        // Choose authentication providers
        val providers = arrayListOf(

                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())
        val loginIntent =  AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.applogo)
                .build()
// Create and launch sign-in intent
        startActivityForResult(
                loginIntent ,
                RC_SIGN_IN)

    }

    override fun onOfferSelectedAvailableOffers(offer: Offer) {
        var offerFragment: OfferDetailFragment? = null
        offerFragment = OfferDetailFragment.newInstance(offer,0, Guser.uid)
        if (Fbool == true) {
            offerFragment = OfferDetailFragment.newInstance(offer,1,Guser.uid)
        }
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, offerFragment)
        ft.addToBackStack("detail")
        ft.commit()
    }

    override fun onOfferSelected(offer: Offer) {

       //Toast.makeText(this, offer.toString(), Toast.LENGTH_LONG).show()
        var offerFragment: OfferDetailFragment? = null
        offerFragment = OfferDetailFragment.newInstance(offer,0, Guser.uid)
        if (Fbool == true) {
            offerFragment = OfferDetailFragment.newInstance(offer,1,Guser.uid)
        }
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, offerFragment)
        ft.addToBackStack("detail")
        ft.commit()
    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var switchTo : Fragment? = null
        when (item.itemId) {
            R.id.navigation_my_offers -> {
                fab.hide()
                Fbool = false
                toolbar.title = "My Posted Offers History"
               // Toast.makeText(this,"Changed", Toast.LENGTH_LONG).show()
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragment_container, MyOffersListFragment.newInstance(Guser.uid , false ))
                ft.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_available_offers -> {
                fab.show()
                Fbool = false
                toolbar.title = "All Available Offers"
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragment_container, AvailableOffersListFragment.newInstance(Guser.uid , false , Guser ))
                ft.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_accepted_offers -> {
                fab.hide()
                Fbool = true
                toolbar.title = "My Accepted Offers"
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragment_container, AcceptedOffersListFragment.newInstance(Guser.uid , false ))
                ft.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_new_offers -> {
                fab.hide()
                Fbool = false
                toolbar.title = "Settings"
                switchTo = SettingsFragment()
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
        initializeListeners()
//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun filterDialog() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_filter,null,false)
        val spinner1 = view.dialog_filter_desired_currency
        val spinner2 = view.dialog_filter_user_currency_filter
        spinner1.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.currencies)) as SpinnerAdapter?
        spinner2.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.currencies)) as SpinnerAdapter?
        builder.setView(view)
        builder.setTitle("Filters")
        builder.setPositiveButton(android.R.string.ok) { _,_ ->
//            val userCurrency  = view.dialog_filter_user_currency_filter.getSelectedItem().toString()
//            val desiredCurrency = view.dialog_add_desired_currency_spinner.getSelectedItem().toString()
//            val ft = supportFragmentManager.beginTransaction()
//            ft.replace(R.id.fragment_container, AvailableOffersListFragment.newInstance(Guser.uid , false , Guser ))
//            ft.commitAllowingStateLoss()
        }
        builder.setNegativeButton(android.R.string.cancel , null)
        builder.create().show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings ->{
//
                filterDialog()
                true}
            R.id.action_logout -> {
                auth.signOut()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
