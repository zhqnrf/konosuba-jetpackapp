package com.example.compose.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import com.example.compose.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    val profileImage = painterResource(id = R.drawable.profil)
    val bannerImage = painterResource(id = R.drawable.banner)
    val name = "Zhaqian Rouf Alfauzi"
    val email = "zhaqianroufa@gmail.com"
    val bio = "Yes i'm bruh"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            title = { Text("About", color = Color.White) },
            backgroundColor = Color(0xFF1DA1F2),
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Transparent)
        ) {
            Image(
                painter = bannerImage,
                contentDescription = "Banner Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Avatar positioned at the center of the banner
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape) // White border on avatar
                    .background(Color.LightGray)
                    .align(Alignment.Center) // Center avatar on banner
                    .graphicsLayer {
                        shadowElevation = 8.dp.toPx()
                    }
            ) {
                Image(
                    painter = profileImage,
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Main content area for text and button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.Start
        ) {
            // Name Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp), // Space between cards
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp
            ) {
                Text(
                    text = name,
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(16.dp) // Padding inside the card
                )
            }

            // Email Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp
            ) {
                Text(
                    text = email,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Bio Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp), // Space before the button
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp
            ) {
                Text(
                    text = bio,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Follow Button
            Button(
                onClick = { /* TODO: Add Action */ },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Follow", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}
