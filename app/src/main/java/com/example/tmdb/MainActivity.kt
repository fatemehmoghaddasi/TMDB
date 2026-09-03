package com.example.tmdb

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tmdb.ui.TmdbApp
import com.example.tmdb.ui.theme.TMDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate:")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBTheme {
                TmdbApp()
            }
        }
    }

    override fun onStart() {
        Log.d("MainActivity", "onStart:")
        super.onStart()
    }

    override fun onResume() {
        Log.d("MainActivity", "onResume:")
        super.onResume()
    }

    override fun onPause() {
        Log.d("MainActivity", "onPause:")
        super.onPause()
    }

    override fun onStop() {
        Log.d("MainActivity", "onStop:")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("MainActivity", "onDestroy:")
        super.onDestroy()
    }
}