package org.ieselcaminas.daniel.proyectoclase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.ieselcaminas.daniel.proyectoclase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        toolbar = binding.toolbar
        drawerLayout = binding.drawerLayout

        setSupportActionBar(toolbar)

        val navController = this.findNavController(R.id.nav_host_fragment)

        navController.setGraph(R.navigation.navigation)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if (destination.id == controller.graph.startDestination) {

            } else {

            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)

        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp()
    }

}
