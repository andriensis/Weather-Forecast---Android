package it.massimilianomassaro.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

/**
 I'm using this activity as a fragment container (because I used the navigation graph library)
 but this could be used to inject a static appbar/bottom bar or any static UI elements
 **/

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}