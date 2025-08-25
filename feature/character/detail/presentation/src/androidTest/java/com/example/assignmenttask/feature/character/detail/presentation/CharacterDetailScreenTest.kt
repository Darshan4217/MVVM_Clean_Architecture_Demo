package com.example.assignmenttask.feature.character.detail.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignmenttask.feature.character.detail.domain.model.Character
import com.example.assignmenttask.feature.character.detail.domain.model.Location
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockCharacter = Character(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "Genius",
        gender = "Male",
        image = "https://test.com/avatar1.jpeg",
        episode = listOf(
            "https://test.com/episode1",
            "https://test.com/episode2",
            "https://test.com/episode3"
        ),
        url = "https://test.com/character1",
        created = "2017-11-04T18:48:46.250Z",
        origin = Location("Earth (C-137)", "https://test.com/location1"),
        location = Location("Earth (Replacement Dimension)", "https://test.com/location20")
    )

    private val mockCharacterWithEmptyType = Character(
        id = 2,
        name = "Morty Smith",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        image = "https://test.com/avatar2.jpeg",
        episode = listOf("https://test.com/episode1"),
        url = "https://test.com/character2",
        created = "2017-11-04T18:50:21.651Z",
        origin = Location("Earth (C-137)", "https://test.com/location1"),
        location = Location("Earth (Replacement Dimension)", "https://test.com/location20")
    )

    @Test
    fun characterDetailScreenShowsLoadingStateInitially() {
        // Given
        val uiState = CharacterDetailUiState.Loading

        // When
        composeTestRule.setContent {
            CharacterDetailScreen(
                uiState = uiState
            )
        }

        // Then
        composeTestRule.onNodeWithTag("error_component").assertDoesNotExist()
        composeTestRule.onNodeWithTag("character_detail_content").assertDoesNotExist()
    }

    @Test
    fun characterDetailScreenShowsSuccessStateWithCharacterDetails() {
        // Given
        val uiState = CharacterDetailUiState.Success(mockCharacter)

        // When
        composeTestRule.setContent {
            CharacterDetailScreen(
                uiState = uiState
            )
        }

        // Then
        composeTestRule.onNodeWithTag("loading_component").assertDoesNotExist()
        composeTestRule.onNodeWithTag("error_component").assertDoesNotExist()

        // Verify character image
        composeTestRule.onNodeWithContentDescription("Character image").assertExists()

        // Verify character name
        composeTestRule.onNodeWithText("Rick Sanchez").assertExists()

        // Verify character details
        composeTestRule.onNodeWithText("Status:").assertExists()
        composeTestRule.onNodeWithText("Alive").assertExists()
        composeTestRule.onNodeWithText("Species:").assertExists()
        composeTestRule.onNodeWithText("Human").assertExists()
        composeTestRule.onNodeWithText("Type:").assertExists()
        composeTestRule.onNodeWithText("Genius").assertExists()
        composeTestRule.onNodeWithText("Gender:").assertExists()
        composeTestRule.onNodeWithText("Male").assertExists()
        composeTestRule.onNodeWithText("Origin:").assertExists()
        composeTestRule.onNodeWithText("Earth (C-137)").assertExists()
        composeTestRule.onNodeWithText("Location:").assertExists()
        composeTestRule.onNodeWithText("Earth (Replacement Dimension)").assertExists()
        composeTestRule.onNodeWithText("Created:").assertExists()
        composeTestRule.onNodeWithText("2017-11-04T18:48:46.250Z").assertExists()

        // Verify episodes section
        composeTestRule.onNodeWithText("Episodes (3)").assertExists()
        composeTestRule.onNodeWithText("https://test.com/episode1").assertExists()
        composeTestRule.onNodeWithText("https://test.com/episode2").assertExists()
        composeTestRule.onNodeWithText("https://test.com/episode3").assertExists()
    }

    @Test
    fun characterDetailScreenShowsUnknownForEmptyType() {
        // Given
        val uiState = CharacterDetailUiState.Success(mockCharacterWithEmptyType)

        // When
        composeTestRule.setContent {
            CharacterDetailScreen(
                uiState = uiState
            )
        }

        // Then
        composeTestRule.onNodeWithText("Type:").assertExists()
        composeTestRule.onNodeWithText("Unknown").assertExists()

    }

    @Test
    fun characterDetailScreenShowsSingleEpisodeCorrectly() {
        // Given
        val singleEpisodeCharacter = mockCharacter.copy(
            episode = listOf("https://test.com/single_episode")
        )
        val uiState = CharacterDetailUiState.Success(singleEpisodeCharacter)

        // When
        composeTestRule.setContent {
            CharacterDetailScreen(
                uiState = uiState
            )
        }

        // Then
        composeTestRule.onNodeWithText("Episodes (1)").assertExists()
        composeTestRule.onNodeWithText("https://test.com/single_episode").assertExists()
    }

    @Test
    fun characterDetailScreenShowsNoEpisodesCorrectly() {
        // Given
        val noEpisodeCharacter = mockCharacter.copy(episode = emptyList())
        val uiState = CharacterDetailUiState.Success(noEpisodeCharacter)

        // When
        composeTestRule.setContent {
            CharacterDetailScreen(
                uiState = uiState
            )
        }

        // Then
        composeTestRule.onNodeWithText("Episodes (0)").assertExists()
    }

    @Test
    fun characterDetailScreenShowsMultipleEpisodesWithCorrectIndentation() {
        // Given
        val multipleEpisodeCharacter = mockCharacter.copy(
            episode = listOf(
                "https://test.com/episode1",
                "https://test.com/episode2",
                "https://test.com/episode3",
                "https://test.com/episode4",
                "https://test.com/episode5"
            )
        )
        val uiState = CharacterDetailUiState.Success(multipleEpisodeCharacter)

        // When
        composeTestRule.setContent {
            CharacterDetailScreen(
                uiState = uiState
            )
        }

        // Then
        composeTestRule.onNodeWithText("Episodes (5)").assertExists()
        composeTestRule.onNodeWithText("https://test.com/episode1").assertExists()
        composeTestRule.onNodeWithText("https://test.com/episode2").assertExists()
        composeTestRule.onNodeWithText("https://test.com/episode3").assertExists()
        composeTestRule.onNodeWithText("https://test.com/episode4").assertExists()
        composeTestRule.onNodeWithText("https://test.com/episode5").assertExists()
    }

    @Test
    fun characterDetailScreenShowsCharacterWithSpecialCharacters() {
        // Given
        val specialCharacter = mockCharacter.copy(
            name = "Rick & Morty",
            status = "Dead",
            species = "Human-Alien Hybrid",
            type = "Special Type (Enhanced)",
            gender = "Unknown"
        )
        val uiState = CharacterDetailUiState.Success(specialCharacter)

        // When
        composeTestRule.setContent {
            CharacterDetailScreen(
                uiState = uiState
            )
        }

        // Then
        composeTestRule.onNodeWithText("Rick & Morty").assertExists()
        composeTestRule.onNodeWithText("Status:").assertExists()
        composeTestRule.onNodeWithText("Dead").assertExists()
        composeTestRule.onNodeWithText("Species:").assertExists()
        composeTestRule.onNodeWithText("Human-Alien Hybrid").assertExists()
        composeTestRule.onNodeWithText("Type:").assertExists()
        composeTestRule.onNodeWithText("Special Type (Enhanced)").assertExists()
        composeTestRule.onNodeWithText("Gender:").assertExists()
        composeTestRule.onNodeWithText("Unknown").assertExists()
    }

    @Test
    fun characterDetailScreenShowsLongTextCorrectly() {
        // Given
        val longTextCharacter = mockCharacter.copy(
            name = "Very Long Character Name That Exceeds Normal Length",
            origin = Location("Very Long Origin Name That Exceeds Normal Length", "https://test.com/long_origin"),
            location = Location("Very Long Location Name That Exceeds Normal Length", "https://test.com/long_location")
        )
        val uiState = CharacterDetailUiState.Success(longTextCharacter)

        // When
        composeTestRule.setContent {
            CharacterDetailScreen(
                uiState = uiState
            )
        }

        // Then
        composeTestRule.onNodeWithText("Very Long Character Name That Exceeds Normal Length").assertExists()
        composeTestRule.onNodeWithText("Origin:").assertExists()
        composeTestRule.onNodeWithText("Very Long Origin Name That Exceeds Normal Length").assertExists()
        composeTestRule.onNodeWithText("Location:").assertExists()
        composeTestRule.onNodeWithText("Very Long Location Name That Exceeds Normal Length").assertExists()
    }

    @Test
    fun characterDetailScreenShowsCharacterWithEmptyEpisodeUrls() {
        // Given
        val emptyEpisodeCharacter = mockCharacter.copy(
            episode = listOf("", "https://test.com/episode1", "")
        )
        val uiState = CharacterDetailUiState.Success(emptyEpisodeCharacter)

        // When
        composeTestRule.setContent {
            CharacterDetailScreen(
                uiState = uiState
            )
        }

        // Then
        composeTestRule.onNodeWithText("Episodes (3)").assertExists()
        composeTestRule.onNodeWithText("https://test.com/episode1").assertExists()
    }

    @Test
    fun characterDetailScreenShowsCharacterWithVeryLongEpisodeUrls() {
        // Given
        val longEpisodeUrls = listOf(
            "https://very-long-domain-name-that-exceeds-normal-length.com/api/episode/very-long-episode-id-that-exceeds-normal-length",
            "https://another-very-long-domain-name-that-exceeds-normal-length.com/api/episode/another-very-long-episode-id-that-exceeds-normal-length"
        )
        val longEpisodeCharacter = mockCharacter.copy(episode = longEpisodeUrls)
        val uiState = CharacterDetailUiState.Success(longEpisodeCharacter)

        // When
        composeTestRule.setContent {
            CharacterDetailScreen(
                uiState = uiState
            )
        }

        // Then
        composeTestRule.onNodeWithText("Episodes (2)").assertExists()
        longEpisodeUrls.forEach { url ->
            composeTestRule.onNodeWithText(url).assertExists()
        }
    }
}
