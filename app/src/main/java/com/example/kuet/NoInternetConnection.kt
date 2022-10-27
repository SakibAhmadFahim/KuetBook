package com.example.kuet

import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi

class NoInternetConnection : AppCompatActivity() {

    private lateinit var internetLayout: RelativeLayout
    private lateinit var noInternetLayout: RelativeLayout

    private lateinit var tryAgainButton: Button

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet_connection)

        noInternetLayout = findViewById(R.id.noInternetLayout)
        tryAgainButton = findViewById(R.id.try_again_button)

        drawLayout()

        tryAgainButton.setOnClickListener {
            drawLayout()
        }

    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)

        return (capabilities != null && capabilities.hasCapability(NET_CAPABILITY_INTERNET))

    }



    @RequiresApi(Build.VERSION_CODES.M)
    private fun drawLayout() {
        if (isNetworkAvailable()) {
            internetLayout.visibility = VISIBLE
            noInternetLayout.visibility = GONE
        } else {
            noInternetLayout.visibility = VISIBLE
            internetLayout.visibility = GONE
        }
    }
}