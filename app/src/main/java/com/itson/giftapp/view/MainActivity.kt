package com.itson.giftapp.view

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.itson.giftapp.R
import com.itson.giftapp.databinding.ActivityMainBinding
import com.itson.giftapp.fragment.*
import com.itson.giftapp.utils.network.ConnectivityObserver
import com.itson.giftapp.utils.network.NetworkConnectivityObserver
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var homeFragment = HomeFragment()
    private lateinit var connectivityObserver: ConnectivityObserver

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setMessage("No Internet Connection")
        builder.setTitle("Attention!")
        builder.setCancelable(false)
        val alertDialog = builder.create()
        val x = Snackbar.make(
            binding.frameLayout,
            R.string.no_internet_connection,
            Snackbar.LENGTH_INDEFINITE
        )

        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        connectivityObserver.observe().onEach {
            if (it == ConnectivityObserver.Status.Available) {
                alertDialog.dismiss()
                Log.i(TAG, "onCreate: status in $it")
            } else if (it == ConnectivityObserver.Status.Lost) {
                Log.i(TAG, "onCreate: status in $it")
                alertDialog.show()
            }
        }.launchIn(lifecycleScope)

        beginTransaction(homeFragment)
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    beginTransaction(homeFragment)
                }
                R.id.action_category -> {
                    beginTransaction(CategoryFragment())
                }
                R.id.action_favorites -> {
                    beginTransaction(FavoriteFragment())
                }
                R.id.action_cart -> {
                    beginTransaction(CartFragment())
                }
                R.id.action_profile -> {
                    redirectToLogin()
                }
            }
            true
        }

        // Agregar onClickListener para el botón de amigos
        binding.friendsButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, FriendsActivity::class.java))
        }
    }
    private fun redirectToLogin() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
        finish() // Opcionalmente, puedes finalizar esta actividad para evitar que el usuario vuelva atrás con el botón de retroceso
    }
    private fun beginTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }
}
