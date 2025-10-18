package com.mahful.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahful.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF5F5F5)
                ) {
                    ArtSpaceScreen()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceScreen() {
    val artworks = listOf(
        Art(R.drawable.gambarnya, "London Is Red Not Blue", "Red Devil", 1878),
    )

    var index by rememberSaveable { mutableStateOf(0) }
    val item = artworks[index]
    val cfg = LocalConfiguration.current
    val isLandscape = cfg.screenWidthDp > cfg.screenHeightDp

    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArtworkFrame(
                imageRes = item.imageRes,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CaptionCard(
                    title = item.title,
                    artist = item.artist,
                    year = item.year,
                    modifier = Modifier.fillMaxWidth()
                )
                NavButtons(
                    onPrev = { index = if (index == 0) artworks.lastIndex else index - 1 },
                    onNext = { index = (index + 1) % artworks.size },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ArtworkFrame(
                imageRes = item.imageRes,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            CaptionCard(
                title = item.title,
                artist = item.artist,
                year = item.year,
                modifier = Modifier.fillMaxWidth()
            )
            NavButtons(
                onPrev = { index = if (index == 0) artworks.lastIndex else index - 1 },
                onNext = { index = (index + 1) % artworks.size },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ArtworkFrame(@DrawableRes imageRes: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun CaptionCard(
    title: String,
    artist: String,
    year: Int?,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFE9EBF2)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "$artist (${year ?: "-"})",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun NavButtons(
    onPrev: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onPrev,
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDCE2F0),
                contentColor = Color.Black
            ),
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            Text("Previous")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = onNext,
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDCE2F0),
                contentColor = Color.Black
            ),
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            Text("Next")
        }
    }
}

data class Art(
    @DrawableRes val imageRes: Int,
    val title: String,
    val artist: String,
    val year: Int?
)
