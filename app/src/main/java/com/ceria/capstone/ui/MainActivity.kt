package com.ceria.capstone.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ceria.capstone.R
import com.ceria.capstone.databinding.ActivityMainBinding
import com.ceria.capstone.utils.gone
import com.ceria.capstone.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
            ViewCompat.setOnApplyWindowInsetsListener(
                bottomBar
            ) { v: View?, _: WindowInsetsCompat? ->
                ViewCompat.onApplyWindowInsets(
                    v!!, WindowInsetsCompat.CONSUMED
                )
            }
            bottomBar.isClickable = false
            val navHost =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHost.navController
            bottomNav.setupWithNavController(navController)
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                when (destination.id) {
                    R.id.loginFragment -> {
                        bottomBar.gone()
                        fabPlay.gone()
                        navHostFragment.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                            bottomMargin = 0
                        }
                    }

                    else -> {
                        bottomBar.visible()
                        fabPlay.visible()
                        bottomBar.viewTreeObserver.addOnGlobalLayoutListener(object :
                            ViewTreeObserver.OnGlobalLayoutListener {
                            override fun onGlobalLayout() {
                                // Remove the listener to avoid multiple calls
                                bottomBar.viewTreeObserver.removeOnGlobalLayoutListener(this)

                                // Now get the height
                                val bottomAppBarHeight = bottomBar.height
                                Timber.d("BottomAppBar height: $bottomAppBarHeight")

                                // Update navHostFragment margin
                                navHostFragment.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                                    bottomMargin = bottomAppBarHeight
                                }
                            }
                        })
                    }
                }
            }
            bottomNav.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.homeFragment -> {
                        navController.navigate(R.id.homeFragment)
                        true
                    }
                    R.id.profileFragment -> {
                        navController.navigate(R.id.profileFragment)
                        true
                    }

                    else -> false

                }
            }
        }
    }
}