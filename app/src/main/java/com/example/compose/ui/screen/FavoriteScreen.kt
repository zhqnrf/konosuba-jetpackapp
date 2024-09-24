package com.example.compose.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.R
import com.example.compose.di.Injection
import com.example.compose.model.Character
import com.example.compose.ui.common.UiState
import com.example.compose.ui.item.EmptyList
import com.example.compose.ui.viewmodel.FavoriteViewModel
import com.example.compose.ui.viewmodel.ViewModelFactory

@Composable
fun FavoriteScreen(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteCharacter()
            }
            is UiState.Success -> {
                FavoriteInformation(
                    listCharacter = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateCharacter(id, newState)
                    }
                )
            }
            is UiState.Error -> {
                // Handle error case if needed
            }
        }
    }
}

@Composable
fun FavoriteInformation(
    listCharacter: List<Character>,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text("Favorit", color = Color.White) },
        backgroundColor = Color(0xFF1DA1F2),
        modifier = Modifier.fillMaxWidth()
    )
    Column(
        modifier = modifier
            .padding(top = 70.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)// Add padding to the column
    ) {

        if (listCharacter.isNotEmpty()) {
            listCharacter.forEach { character ->
                CharacterCard(
                    character = character,
                    onFavoriteIconClicked = onFavoriteIconClicked,
                    navigateToDetail = navigateToDetail,
                    modifier = Modifier.padding(bottom = 8.dp) // Space between cards
                )
            }
        } else {
            EmptyList(
                Warning = stringResource(R.string.empty_favorite)
            )
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { navigateToDetail(character.id) }, // Navigate to detail on card click
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFE9F1F5) // Light background color
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp) // Padding inside the card
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically // Center align items vertically
        ) {
            // Display character image as a circular shape
            Image(
                painter = painterResource(character.image), // Use character's image resource ID
                contentDescription = character.name,
                modifier = Modifier
                    .size(60.dp) // Adjust size as needed
                    .clip(RoundedCornerShape(30.dp)) // Make the image circular
                    .border(2.dp, Color.Gray, RoundedCornerShape(30.dp)) // Optional border for styling
            )

            Spacer(modifier = Modifier.width(16.dp)) // Space between image and text

            Column(
                modifier = Modifier.weight(1f) // Take up remaining space
            ) {
                Text(
                    text = character.name,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = character.positional, // Add any additional details here
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Love rating or favorite icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorite Icon",
                        modifier = Modifier
                            .clickable {
                                onFavoriteIconClicked(character.id, !character.isFavorite) // Update favorite state
                            }
                            .size(24.dp)
                            .padding(start = 8.dp),
                        tint = if (character.isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
    }
}

