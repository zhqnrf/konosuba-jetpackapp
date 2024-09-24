package com.example.compose.ui.screen

import com.example.compose.ui.viewmodel.DetailViewModel
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import com.example.compose.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.di.Injection
import com.example.compose.ui.common.UiState
import com.example.compose.ui.viewmodel.ViewModelFactory

@Composable
fun DetailScreen(
    characterId: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getCharacterById(characterId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailInformation(
                    image = data.image,
                    id = data.id,
                    name = data.name,
                    description = data.description,
                    positional = data.positional,
                    active = data.active,
                    rating = data.rating,
                    origin = data.origin, // Menambahkan parameter origin
                    gender = data.gender, // Menambahkan parameter gender
                    isFavorite = data.isFavorite,
                    navigateBack = navigateBack,
                    onFavoriteButtonClicked = { id, state ->
                        viewModel.updateCharacter(id, state)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailInformation(
    id: Int,
    name: String,
    @DrawableRes image: Int,
    description: String,
    positional: String,
    active: String,
    rating: Double,
    origin: String, // Menambahkan parameter origin
    gender: String, // Menambahkan parameter gender
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6)) // Warna latar belakang
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
                .padding(16.dp) // Padding di sekitar kolom
        ) {

            Image(
                painter = painterResource(image),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Tinggi yang disesuaikan untuk tata letak visual yang lebih baik
                    .clip(CircleShape) // Sudut membulat
                    .testTag("scrollToBottom")
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp, // Ukuran font lebih besar
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Layout Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                InfoRow(icon = Icons.Default.Star, text = rating.toString()) // Rating
                InfoRow(icon = Icons.Default.PersonOutline, text = positional) // Ikon untuk posisi
                InfoRow(icon = Icons.Default.ThumbUp, text = active) // Ikon untuk status aktif
                InfoRow(icon = Icons.Default.Home, text = origin) // Ikon untuk asal
                InfoRow(icon = Icons.Default.Wc, text = gender) // Ikon untuk jenis kelamin
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text(
                text = description,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Tombol Navigasi dan Favorit
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.White)
                .testTag("back_home")
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
            )
        }
        IconButton(
            onClick = {
                onFavoriteButtonClicked(id, isFavorite)
            },
            modifier = Modifier
                .padding(end = 16.dp, top = 8.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.White)
                .testTag("favorite_detail_button")
        ) {
            Icon(
                imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                contentDescription = if (!isFavorite) stringResource(R.string.add_favorite) else stringResource(R.string.delete_favorite),
                tint = if (!isFavorite) Color.Black else Color.Red
            )
        }
    }
}

@Composable
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 4.dp) // Jarak antara baris
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp) // Ukuran ikon yang lebih besar
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f), // Mengambil ruang yang tersedia
            fontSize = 16.sp
        )
    }
}
