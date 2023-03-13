package com.movies.pagination_library_3.presentation

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.movies.pagination_library_3.MAIN
import com.movies.pagination_library_3.R
import com.movies.pagination_library_3.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        supportActionBar?.hide()

        setContentView(R.layout.fragment_splash)

        MAIN = this

        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            supportActionBar?.show()
            setContentView(mBinding.root)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            this@MainActivity.navController = navController
        }



        if (!isNetworkConnected()) {
            // Show error message and close the app
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
        }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}