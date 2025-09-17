package com.example.thiep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.thiep.ui.theme.ThiepTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.wrapContentHeight


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThiepTheme {
                Surface (
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingImage(
                        message_01 = stringResource(R.string.text_1),
                        message_02 = stringResource(R.string.text_2),
                        message_03 = stringResource(R.string.text_3),
                        modifier = Modifier
                    )
                }
            }
        }
    }
}


@Composable
fun GreetingImage(message_01 : String, message_02 : String,message_03 : String, modifier : Modifier = Modifier) {
    val image = painterResource(R.drawable.bg_compose_background)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

        GreetingText(
            message_01 = message_01,
            message_02 = message_02,
            message_03 = message_03,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun GreetingText(message_01: String, message_02 : String, message_03 : String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = message_01,
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(20.dp)
        )

        Text (
            text = message_02,
            fontSize = 10.sp,
            modifier = Modifier
                .padding(10.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )

        Text (
            text = message_03,
            fontSize = 10.sp,
            modifier = Modifier
                .padding(10.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ThiepTheme {
        GreetingText("Android", "babycute", "lyz")
    }
}