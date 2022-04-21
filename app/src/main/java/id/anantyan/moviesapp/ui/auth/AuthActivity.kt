package id.anantyan.moviesapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.databinding.ActivityAuthBinding
import id.anantyan.moviesapp.ui.main.MainActivity
import id.anantyan.utils.sharedPreferences.preference

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Navigation Controller
         * */
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_auth) as NavHostFragment
        navController = navHost.navController
        NavigationUI.setupWithNavController(binding.toolbar, navController)

        if (application.preference().getLogIn() != false && application.preference().getUserId() != -1) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}