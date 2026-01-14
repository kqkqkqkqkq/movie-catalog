package k.moviecatalog

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import k.moviecatalog.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.bottomNavigation.setupWithNavController(navController)

        setupNavigation()
    }

    private fun setupNavigation() {
        lifecycleScope.launch(App.instance.dispatcherProvider.main) {
            App.instance.tokenRepository.token.collect { token ->
                if (token != null) {
                    navController.setGraph(R.navigation.main_navigation)
                    setupBottomNavigationWithNavController()
                } else {
                    navController.setGraph(R.navigation.auth_navigation)
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    private fun setupBottomNavigationWithNavController() {
        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.isVisible = when (destination.id) {
                R.id.navigation_main,
                R.id.navigation_profile,
                R.id.navigation_collections -> true

                else -> false
            }
        }
    }
}
