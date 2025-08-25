package com.example.assignmenttask.feature.character.list.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignmenttask.feature.character.list.domain.model.Character
import com.example.assignmenttask.feature.character.list.domain.model.Location
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockCharacters = listOf(
        Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            image = "https://test.com/avatar1.jpeg",
            episode = listOf("https://test.com/episode1"),
            url = "https://test.com/character1",
            created = "2017-11-04T18:48:46.250Z",
            origin = Location("Earth (C-137)", "https://test.com/location1"),
            location = Location("Earth (Replacement Dimension)", "https://test.com/location20")
        ),
        Character(
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
    )

    @Test
    fun characterListScreenShowsLoadingStateInitially() {
        // Given
        val uiState = CharacterListUiState.Loading

        // When
        composeTestRule.setContent {
            CharacterListScreen(
                uiState = uiState,
                onCharacterClick = {}
            )
        }

        // Then
        composeTestRule.onNodeWithTag("error_component").assertDoesNotExist()
        composeTestRule.onNodeWithTag("character_list").assertDoesNotExist()
    }

    @Test
    fun characterListScreenShowsSuccessStateWithCharacterList() {
        // Given
        val uiState = CharacterListUiState.Success(mockCharacters)
        var clickedCharacter: Character? = null

        // When
        composeTestRule.setContent {
            CharacterListScreen(
                uiState = uiState,
                onCharacterClick = { character -> clickedCharacter = character }
            )
        }

        // Then
        composeTestRule.onNodeWithTag("loading_component").assertDoesNotExist()
        composeTestRule.onNodeWithTag("error_component").assertDoesNotExist()

        // Verify character items are displayed
        composeTestRule.onNodeWithText("Rick Sanchez").assertExists()
        composeTestRule.onNodeWithText("Morty Smith").assertExists()
    }

    @Test
    fun characterItemDisplaysCharacterInformationCorrectly() {
        // Given
        val character = mockCharacters[0]
        var clickedCharacter: Character? = null

        // When
        composeTestRule.setContent {
            CharacterItem(
                character = character,
                onClick = { clickedCharacter = character }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Rick Sanchez").assertExists()
        composeTestRule.onNodeWithText("Human").assertExists()
        composeTestRule.onNodeWithText("Alive").assertExists()
        
        // Verify image exists (AsyncImage will show placeholder initially)
        composeTestRule.onNodeWithContentDescription("Character image").assertExists()
    }

    @Test
    fun characterItemIsClickableAndCallsOnClick() {
        // Given
        val character = mockCharacters[0]
        var clickedCharacter: Character? = null

        // When
        composeTestRule.setContent {
            CharacterItem(
                character = character,
                onClick = { clickedCharacter = character }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Rick Sanchez").performClick()
        assert(clickedCharacter == character)
    }

    @Test
    fun characterListDisplaysAllCharactersInScrollableList() {
        // Given
        var clickedCharacter: Character? = null

        // When
        composeTestRule.setContent {
            CharacterList(
                characters = mockCharacters,
                onCharacterClick = { character -> clickedCharacter = character }
            )
        }

        // Then
        // Verify both characters are displayed
        composeTestRule.onNodeWithText("Rick Sanchez").assertExists()
        composeTestRule.onNodeWithText("Morty Smith").assertExists()

    }

    @Test
    fun characterListCallsOnCharacterClickWhenCharacterIsClicked() {
        // Given
        val character = mockCharacters[0]
        var clickedCharacter: Character? = null

        // When
        composeTestRule.setContent {
            CharacterList(
                characters = mockCharacters,
                onCharacterClick = { character -> clickedCharacter = character }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Rick Sanchez").performClick()
        assert(clickedCharacter == character)
    }

    @Test
    fun characterListScreenHandlesEmptyCharacterList() {
        // Given
        val uiState = CharacterListUiState.Success(emptyList())
        var onCharacterClickCalled = false

        // When
        composeTestRule.setContent {
            CharacterListScreen(
                uiState = uiState,
                onCharacterClick = { onCharacterClickCalled = true })
        }

        // Empty list should still show the list container but no items
        composeTestRule.onNodeWithText("Rick Sanchez").assertDoesNotExist()
        composeTestRule.onNodeWithText("Morty Smith").assertDoesNotExist()
    }

    @Test
    fun characterListScreenHandlesSingleCharacterInList() {
        // Given
        val singleCharacter = listOf(mockCharacters[0])
        val uiState = CharacterListUiState.Success(singleCharacter)
        var clickedCharacter: Character? = null

        // When
        composeTestRule.setContent {
            CharacterListScreen(
                uiState = uiState,
                onCharacterClick = { character -> clickedCharacter = character }
            )
        }

        // Then
        composeTestRule.onNodeWithText("Rick Sanchez").assertExists()
        composeTestRule.onNodeWithText("Morty Smith").assertDoesNotExist()
        
        // Verify only one character item exists
        composeTestRule.onNodeWithText("Rick Sanchez").assertExists()
    }
}
