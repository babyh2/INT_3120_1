package com.example.vatchanhbovo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vatchanhbovo.ui.theme.VatChanhBoVoTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.style.TextAlign


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VatChanhBoVoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VatChanh()
                }
            }
        }
    }
}

@Composable
fun VatChanh() {
    val images = listOf(
        R.drawable.lemon_tree,
        R.drawable.lemon_squeeze,
        R.drawable.lemon_drink,
        R.drawable.lemon_restart,
    )


    var currentIndex by remember { mutableStateOf(0) }

    val texts = listOf(
        R.string.v1,
        R.string.v2,
        R.string.v3,
        R.string.v4
    )

    val contents = listOf(
        R.string.b1,
        R.string.b2,
        R.string.b3,
        R.string.b4
    )


    var requiredClicks by remember { mutableStateOf(0) }
    var currentClicks by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color(0xFFFFD600))
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(text = "Lemonade", color = Color.Black,modifier = Modifier.fillMaxSize()
                    .wrapContentSize(Alignment.Center))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .padding(top = 180.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .clip(RoundedCornerShape(16.dp)) // bo góc tròn 16dp
                    .background(Color.Cyan),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(images[currentIndex]),
                    contentDescription = stringResource(contents[currentIndex]),
                    modifier = Modifier.size(200.dp)
                        .clickable {
                            if (currentIndex == 1) {
                                if (requiredClicks == 0) {
                                    requiredClicks = (2..4).random()
                                    currentClicks = 0
                                }

                                currentClicks++

                                if (currentClicks >= requiredClicks) {
                                    requiredClicks = 0
                                    currentClicks = 0
                                    currentIndex = (currentIndex + 1) % images.size
                                }
                            } else {
                                currentIndex = (currentIndex + 1) % images.size
                            }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(texts[currentIndex]),
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}