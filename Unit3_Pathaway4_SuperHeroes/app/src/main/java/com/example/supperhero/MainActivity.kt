package com.example.supperhero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.supperhero.data.HeroesRepository
import com.example.supperhero.ui.theme.SupperHeroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SupperHeroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppRoot()
                }
            }
        }
    }
}

@Composable
fun AppRoot() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppHeader() }
    ) { innerPadding ->
        val heroes = HeroesRepository.heroes
        HeroesList(heroes = heroes, contentPadding = innerPadding)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge
            )
        },
        modifier = modifier
    )
}

/**
 * Preview để xem nhanh trong Android Studio — giữ hành vi preview giống trước.
 */
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    SupperHeroTheme {
        AppRoot()
    }
}
