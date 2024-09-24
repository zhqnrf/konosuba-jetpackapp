package com.example.compose.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.compose.model.Character
import com.example.compose.ui.theme.SubmissionComposeTheme
import org.junit.Before
import com.example.compose.R
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeDataCharacter = Character(
        id = 0,
        name = "Kazuma Satou",
        image = R.drawable.kazuma, // Ganti dengan gambar Kazuma
        description = "Kazuma Satou is the main protagonist of Konosuba. He is a gamer who is reincarnated in a fantasy world after dying in an embarrassing accident. He is a strategic thinker and often finds clever ways to overcome challenges.",
        positional = "Adventurer",
        rating = 4.5,
        active = "Active",
        origin = "Japan",
        gender = "Male"
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionComposeTheme {
                DetailInformation(
                    id = fakeDataCharacter.id,
                    name = fakeDataCharacter.name,
                    image = fakeDataCharacter.image,
                    description = fakeDataCharacter.description,
                    positional = fakeDataCharacter.positional,
                    active = fakeDataCharacter.active,
                    rating = fakeDataCharacter.rating,
                    isFavorite = fakeDataCharacter.isFavorite,
                    origin = fakeDataCharacter.origin,
                    gender = fakeDataCharacter.gender,
                    navigateBack = {},
                    onFavoriteButtonClicked = {_, _ ->}
                )
            }
        }
    }

    @Test
    fun detailInformation_isDisplayed() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
        composeTestRule.onNodeWithText(fakeDataCharacter.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataCharacter.gender).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataCharacter.origin).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataCharacter.positional).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataCharacter.description).assertIsDisplayed()
    }

    @Test
    fun addToFavoriteButton_hasClickAction() {
        composeTestRule.onNodeWithTag("favorite_detail_button").assertHasClickAction()
    }

    @Test
    fun detailInformation_isScrollable() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
    }

    @Test
    fun favoriteButton_hasCorrectStatus() {
        // Assert that the favorite button is displayed
        composeTestRule.onNodeWithTag("favorite_detail_button").assertIsDisplayed()

        // Assert that the content description of the favorite button is correct based on the isFavorite state
        val isFavorite = fakeDataCharacter.isFavorite // Set the isFavorite state here
        val expectedContentDescription = if (isFavorite) {
            "Remove from Favorite"
        } else {
            "Add to Favorite"
        }

        composeTestRule.onNodeWithTag("favorite_detail_button")
            .assertContentDescriptionEquals(expectedContentDescription)
    }
}