package id.anantyan.moviesapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.databinding.ActivityMainBinding
import id.anantyan.moviesapp.ui.auth.AuthActivity
import id.anantyan.moviesapp.ui.dialog.ProfileDialog
import id.anantyan.moviesapp.ui.dialog.ProfileDialogHelper
import id.anantyan.utils.checkInternet
import id.anantyan.utils.sharedPreferences.preference

class MainActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener,
    Toolbar.OnMenuItemClickListener {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val dialog: ProfileDialogHelper by lazy { ProfileDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applicationContext.checkInternet().observe(this) {
            if (it == false) {
                onSnackbar("No internet access!")
            }
        }

        /**
         * Navigation Controller
         * */
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        navController = navHost.navController
        navController.addOnDestinationChangedListener(this)
        val appBar = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.profileFragment
            )
        )
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBar)

        binding.toolbar.setOnMenuItemClickListener(this)
        binding.bottomNavbar.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment -> {
                binding.toolbar.menu.findItem(R.id.logoutAppBar).isVisible = false
                binding.toolbar.menu.findItem(R.id.favoriteAppBar).isVisible = true
                binding.bottomNavbar.visibility = View.VISIBLE
            }
            R.id.profileFragment -> {
                binding.toolbar.menu.findItem(R.id.logoutAppBar).isVisible = true
                binding.toolbar.menu.findItem(R.id.favoriteAppBar).isVisible = false
                binding.bottomNavbar.visibility = View.VISIBLE
            }
            R.id.homeDetailFragment -> {
                binding.toolbar.menu.findItem(R.id.logoutAppBar).isVisible = false
                binding.toolbar.menu.findItem(R.id.favoriteAppBar).isVisible = false
                binding.bottomNavbar.visibility = View.GONE
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.logoutAppBar -> {
                dialog.dialogLogout {
                    applicationContext.preference().setLogIn(false)
                    applicationContext.preference().setUserId(-1)
                    it.dismiss()
                    val intent = Intent(this, AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    finish()
                    startActivity(intent)
                }
                false
            }
            R.id.favoriteAppBar -> {
                true
            }
            else -> true
        }
    }

    private fun onSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(this, R.color.error))
        snackbar.show()
    }
}