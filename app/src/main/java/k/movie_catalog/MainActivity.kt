package k.movie_catalog

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import k.movie_catalog.databinding.ActivityMainBinding

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
        binding.navView.setupWithNavController(navController)
        setupNavigation()
    }


    private fun setupNavigation() {
        val isAuthorized = true
        if (isAuthorized) {
            navController.setGraph(R.navigation.main_navigation)
            binding.navView.visibility = View.VISIBLE
        } else {
            navController.setGraph(R.navigation.auth_navigation)
            binding.navView.visibility = View.GONE
        }
//        lifecycleScope.launch {
//            App.appComponent.tokenRepository.isAuthorized().collect { isAuthorized ->
//
//            }
//        }
    }
}
