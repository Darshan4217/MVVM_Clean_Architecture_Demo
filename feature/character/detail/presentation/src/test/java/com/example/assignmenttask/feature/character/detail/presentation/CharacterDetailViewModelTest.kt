package com.example.assignmenttask.feature.character.detail.presentation

import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.feature.character.detail.domain.model.Character
import com.example.assignmenttask.feature.character.detail.domain.model.Location
import com.example.assignmenttask.feature.character.detail.domain.usecase.GetCharacterByIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {

    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var mockUseCase: GetCharacterByIdUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val mockCharacter = Character(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "Genius",
        gender = "Male",
        origin = Location("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
        location = Location("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20"),
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        episode = listOf("https://rickandmortyapi.com/api/episode/1"),
        url = "https://rickandmortyapi.com/api/character/1",
        created = "2017-11-04T18:48:46.250Z"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockUseCase = mockk()
        viewModel = CharacterDetailViewModel(mockUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        // Given - ViewModel is created with default state
        
        // Then
        assertTrue(viewModel.uiState.value is CharacterDetailUiState.Loading)
    }

    @Test
    fun `setCharacterId should update characterId and trigger loadCharacter`() = runTest {
        // Given
        val newCharacterId = 5
        coEvery { mockUseCase(newCharacterId) } returns flowOf(Result.Success(mockCharacter))

        // When
        viewModel.setCharacterId(newCharacterId)

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Success)
        
        val successState = currentState as CharacterDetailUiState.Success
        assertEquals(mockCharacter, successState.character)
    }

    @Test
    fun `loadCharacter should emit Loading then Success when use case returns success`() = runTest {
        // Given
        val characterId = 1
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Success(mockCharacter))

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Success)
        
        val successState = currentState as CharacterDetailUiState.Success
        assertEquals(mockCharacter, successState.character)
    }

    @Test
    fun `loadCharacter should emit Loading then Success with correct character data`() = runTest {
        // Given
        val characterId = 1
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Success(mockCharacter))

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Success)
        
        val successState = currentState as CharacterDetailUiState.Success
        val character = successState.character

        assertEquals(1, character.id)
        assertEquals("Rick Sanchez", character.name)
        assertEquals("Alive", character.status)
        assertEquals("Human", character.species)
        assertEquals("Genius", character.type)
        assertEquals("Male", character.gender)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/1.jpeg", character.image)
        assertEquals("https://rickandmortyapi.com/api/character/1", character.url)
        assertEquals("2017-11-04T18:48:46.250Z", character.created)
        assertEquals(1, character.episode.size)
        assertEquals("https://rickandmortyapi.com/api/episode/1", character.episode[0])
    }

    @Test
    fun `loadCharacter should emit Loading then Success with correct location data`() = runTest {
        // Given
        val characterId = 1
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Success(mockCharacter))

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Success)
        
        val successState = currentState as CharacterDetailUiState.Success
        val character = successState.character

        // Check origin location
        assertEquals("Earth (C-137)", character.origin.name)
        assertEquals("https://rickandmortyapi.com/api/location/1", character.origin.url)

        // Check current location
        assertEquals("Earth (Replacement Dimension)", character.location.name)
        assertEquals("https://rickandmortyapi.com/api/location/20", character.location.url)
    }

    @Test
    fun `loadCharacter should emit Loading then Success with empty type`() = runTest {
        // Given
        val characterWithEmptyType = mockCharacter.copy(type = "")
        val characterId = 1
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Success(characterWithEmptyType))

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Success)
        
        val successState = currentState as CharacterDetailUiState.Success
        val character = successState.character

        assertEquals("", character.type)
    }

    @Test
    fun `loadCharacter should emit Loading then Success with multiple episodes`() = runTest {
        // Given
        val episodes = listOf(
            "https://rickandmortyapi.com/api/episode/1",
            "https://rickandmortyapi.com/api/episode/2",
            "https://rickandmortyapi.com/api/episode/3"
        )
        val characterWithMultipleEpisodes = mockCharacter.copy(episode = episodes)
        val characterId = 1
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Success(characterWithMultipleEpisodes))

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Success)
        
        val successState = currentState as CharacterDetailUiState.Success
        val character = successState.character

        assertEquals(3, character.episode.size)
        assertEquals("https://rickandmortyapi.com/api/episode/1", character.episode[0])
        assertEquals("https://rickandmortyapi.com/api/episode/2", character.episode[1])
        assertEquals("https://rickandmortyapi.com/api/episode/3", character.episode[2])
    }

    @Test
    fun `loadCharacter should emit Loading then Error when use case returns error`() = runTest {
        // Given
        val characterId = 1
        val exception = Exception("Network error occurred")
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Error(exception))

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Error)
        
        val errorState = currentState as CharacterDetailUiState.Error
        assertEquals("Network error occurred", errorState.message)
    }

    @Test
    fun `loadCharacter should emit Loading then Error with unknown error when exception message is null`() = runTest {
        // Given
        val characterId = 1
        val exception = Exception()
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Error(exception))

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Error)
        
        val errorState = currentState as CharacterDetailUiState.Error
        assertEquals("Unknown error", errorState.message)
    }

    @Test
    fun `loadCharacter should emit Loading then Error with different exception types`() = runTest {
        // Given
        val characterId = 1
        val runtimeException = RuntimeException("Runtime error")
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Error(runtimeException))

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Error)
        
        val errorState = currentState as CharacterDetailUiState.Error
        assertEquals("Runtime error", errorState.message)
    }

    @Test
    fun `loadCharacter should handle Loading state from use case`() = runTest {
        // Given
        val characterId = 1
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Loading)

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Loading)
    }

    @Test
    fun `setCharacterId should work with different character IDs`() = runTest {
        // Given
        val characterId1 = 1
        val characterId2 = 2
        val character2 = mockCharacter.copy(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human"
        )
        
        coEvery { mockUseCase(characterId1) } returns flowOf(Result.Success(mockCharacter))
        coEvery { mockUseCase(characterId2) } returns flowOf(Result.Success(character2))

        // When & Then - First character
        viewModel.setCharacterId(characterId1)
        testDispatcher.scheduler.advanceUntilIdle()
        val state1 = viewModel.uiState.value as CharacterDetailUiState.Success
        assertEquals(1, state1.character.id)
        assertEquals("Rick Sanchez", state1.character.name)

        // When & Then - Second character
        viewModel.setCharacterId(characterId2)
        testDispatcher.scheduler.advanceUntilIdle()
        val state2 = viewModel.uiState.value as CharacterDetailUiState.Success
        assertEquals(2, state2.character.id)
        assertEquals("Morty Smith", state2.character.name)
    }

    @Test
    fun `setCharacterId should handle error for invalid character ID`() = runTest {
        // Given
        val invalidCharacterId = 999
        val exception = Exception("Character not found")
        coEvery { mockUseCase(invalidCharacterId) } returns flowOf(Result.Error(exception))

        // When
        viewModel.setCharacterId(invalidCharacterId)

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Error)
        
        val errorState = currentState as CharacterDetailUiState.Error
        assertEquals("Character not found", errorState.message)
    }

    @Test
    fun `loadCharacter should handle character with special characters in name`() = runTest {
        // Given
        val characterWithSpecialName = mockCharacter.copy(name = "Rick & Morty's Dad")
        val characterId = 1
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Success(characterWithSpecialName))

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Success)
        
        val successState = currentState as CharacterDetailUiState.Success
        val character = successState.character

        assertEquals("Rick & Morty's Dad", character.name)
    }

    @Test
    fun `loadCharacter should handle character with very long strings`() = runTest {
        // Given
        val longString = "A".repeat(1000)
        val characterWithLongStrings = mockCharacter.copy(
            name = longString,
            type = longString,
            url = longString
        )
        val characterId = 1
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Success(characterWithLongStrings))

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Success)
        
        val successState = currentState as CharacterDetailUiState.Success
        val character = successState.character

        assertEquals(longString, character.name)
        assertEquals(longString, character.type)
        assertEquals(longString, character.url)
    }

    @Test
    fun `loadCharacter should handle character with empty episode list`() = runTest {
        // Given
        val characterWithEmptyEpisodes = mockCharacter.copy(episode = emptyList())
        val characterId = 1
        coEvery { mockUseCase(characterId) } returns flowOf(Result.Success(characterWithEmptyEpisodes))

        // When
        viewModel.loadCharacter()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Success)
        
        val successState = currentState as CharacterDetailUiState.Success
        val character = successState.character

        assertTrue(character.episode.isEmpty())
    }

    @Test
    fun `multiple rapid setCharacterId calls should handle correctly`() = runTest {
        // Given
        val characterId1 = 1
        val characterId2 = 2
        val character2 = mockCharacter.copy(id = 2, name = "Morty Smith")
        
        coEvery { mockUseCase(characterId1) } returns flowOf(Result.Success(mockCharacter))
        coEvery { mockUseCase(characterId2) } returns flowOf(Result.Success(character2))

        // When - Rapid calls
        viewModel.setCharacterId(characterId1)
        viewModel.setCharacterId(characterId2)

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterDetailUiState.Success)
        
        val successState = currentState as CharacterDetailUiState.Success
        assertEquals(2, successState.character.id)
        assertEquals("Morty Smith", successState.character.name)
    }
}

