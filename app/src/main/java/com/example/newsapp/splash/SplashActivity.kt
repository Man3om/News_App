package com.example.newsapp.splash

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.ui.theme.NewsAppTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

        setContent {
            NewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SplashContent()
                }
            }

        }
    }

    @Composable
    fun SplashContent(modifier: Modifier = Modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier)
            Image(
                painter = painterResource(if (isSystemInDarkTheme()) R.drawable.ic_app_logo_night else R.drawable.ic_app_logo),
                contentDescription = stringResource(R.string.news_app_logo),
            )

            Image(
                painter = painterResource(if (isSystemInDarkTheme()) R.drawable.ic_signature_night else R.drawable.ic_signature),
                contentDescription = stringResource(R.string.news_app_signature),
            )
        }
    }

    @Composable
    @Preview(showSystemUi = true)
    fun SplashContentPreview(modifier: Modifier = Modifier) {
        SplashContent()
    }
}