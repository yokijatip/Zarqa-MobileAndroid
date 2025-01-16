package com.gity.myzarqa.presentation.common.base

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.gity.myzarqa.R
import com.gity.myzarqa.databinding.ActivityMainBinding
import com.gity.myzarqa.presentation.history.HistoryFragment
import com.gity.myzarqa.presentation.home.HomeFragment
import com.gity.myzarqa.presentation.inventory.InventoryFragment
import com.gity.myzarqa.presentation.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment? = null
    private var currentMenuId: Int = R.id.bottom_menu_home // Track current menu position

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        if (savedInstanceState == null) {
            navigateToFragment(HomeFragment(), R.id.bottom_menu_home)
            binding.bottomNavigationBar.setItemSelected(R.id.bottom_menu_home)
        }
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        binding.bottomNavigationBar.setOnItemSelectedListener { id ->
            when (id) {
                R.id.bottom_menu_home -> navigateToFragment(HomeFragment(), id)
                R.id.bottom_menu_inventory -> navigateToFragment(InventoryFragment(), id)
                R.id.bottom_menu_history -> navigateToFragment(HistoryFragment(), id)
                R.id.bottom_menu_settings -> navigateToFragment(SettingsFragment(), id)
            }
        }
    }

    private fun getAnimationDirection(newMenuId: Int): AnimDirection {
        // Get menu positions
        val menuIds = listOf(
            R.id.bottom_menu_home,
            R.id.bottom_menu_inventory,
            R.id.bottom_menu_history,
            R.id.bottom_menu_settings
        )

        val currentPosition = menuIds.indexOf(currentMenuId)
        val newPosition = menuIds.indexOf(newMenuId)

        return if (newPosition > currentPosition) {
            AnimDirection.RIGHT
        } else {
            AnimDirection.LEFT
        }
    }

    private fun navigateToFragment(fragment: Fragment, menuId: Int): Boolean {
        // Prevent recreation of the same fragment
        if (currentFragment?.javaClass == fragment.javaClass) {
            return false
        }

        val direction = getAnimationDirection(menuId)

        val (enterAnim, exitAnim) = when (direction) {
            AnimDirection.RIGHT -> Pair(R.anim.slide_in_right, R.anim.slide_out_left)
            AnimDirection.LEFT -> Pair(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        currentFragment = fragment
        currentMenuId = menuId // Update current menu position

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                enterAnim,
                exitAnim,
                enterAnim,
                exitAnim
            )
            .replace(R.id.fragment_container, fragment)
            .commit()

        return true
    }

    private enum class AnimDirection {
        LEFT, RIGHT
    }

    private fun setupUI() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Handle back press
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.bottomNavigationBar.getSelectedItemId() == R.id.bottom_menu_home) {
            super.onBackPressed()
        } else {
            binding.bottomNavigationBar.setItemSelected(R.id.bottom_menu_home)
        }
    }
}