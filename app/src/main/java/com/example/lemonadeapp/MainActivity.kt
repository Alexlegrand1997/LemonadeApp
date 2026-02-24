package com.example.lemonadeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonadeapp.ui.theme.LemonadeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeAppTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)) {
//                    Text(R.string.app_name)
                    Lemonade(modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center))
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Lemonade(modifier: Modifier = Modifier) {
    var lemonadeState by remember { mutableIntStateOf(1) }
    var lemonadeSqueezeRemaining by remember { mutableIntStateOf((2..4).random()) }

    val lemonadeVisual = when (lemonadeState){
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val lemonadeInstruction = when (lemonadeState){
        1 -> R.string.lemon_tree
        2 -> R.string.lemon_squeeze
        3 -> R.string.lemon_drink
        else -> R.string.lemon_restart
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize().
                padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    onClick = {
                        lemonadeState = when (lemonadeState) {
                            1 -> 2
                            2 -> {
                                lemonadeSqueezeRemaining--
                                if (lemonadeSqueezeRemaining == 0) {
                                    3
                                } else {
                                    2
                                }
                            }

                            3 -> 4
                            else -> {
                                lemonadeSqueezeRemaining = (2..4).random()
                                1
                            }
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Image(
                        painter = painterResource(lemonadeVisual),
                        contentDescription = lemonadeState.toString(),
                        contentScale = ContentScale.Crop
                    )

                }

                Spacer(Modifier.height(16.dp))

                Text(
                    stringResource(lemonadeInstruction, lemonadeSqueezeRemaining.toString()),
                    fontSize = 18.sp
                )

            }
        }
    }
}

@Preview
@Composable
fun LemonadePreview(){
    Lemonade(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center))
}

