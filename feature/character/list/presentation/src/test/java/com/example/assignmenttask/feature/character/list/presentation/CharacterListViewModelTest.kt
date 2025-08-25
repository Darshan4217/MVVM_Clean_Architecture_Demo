package com.example.assignmenttask.feature.character.list.presentation

import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.feature.character.list.domain.model.Character
import com.example.assignmenttask.feature.character.list.domain.model.CharacterList
import com.example.assignmenttask.feature.character.list.domain.model.Info
import com.example.assignmenttask.feature.character.list.domain.model.Location
import com.example.assignmenttask.feature.character.list.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import io.mockk.mockk
import io.mockk.every

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {

    private lateinit var viewModel: CharacterListViewModel
    private lateinit var mockUseCase: GetCharactersUseCase
    private val testDispatcher = StandardTestDispatcher()

    private val mockCharacters = listOf(
        Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z",
            origin = Location("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            location = Location("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20")
        ),
        Character(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/2",
            created = "2017-11-04T18:50:21.651Z",
            origin = Location("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            location = Location("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20")
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockUseCase = mockk()
        // Set up default mock behavior before creating ViewModel
        every { mockUseCase() } returns flowOf(Result.Loading)
        viewModel = CharacterListViewModel(mockUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be Loading`() = runTest {
        // Given - ViewModel is already created with Loading state in init
        
        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.uiState.value is CharacterListUiState.Loading)
    }

    @Test
    fun `should emit Success state when use case returns success`() = runTest {
        // Given
        val characterList = CharacterList(
            info = Info(count = 2, pages = 1, next = null, prev = null),
            results = mockCharacters
        )
        every { mockUseCase() } returns flowOf(Result.Success(characterList))

        // When
        viewModel.loadCharacters()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterListUiState.Success)
        
        val successState = currentState as CharacterListUiState.Success
        assertEquals(2, successState.characters.size)
        assertEquals("Rick Sanchez", successState.characters[0].name)
        assertEquals("Morty Smith", successState.characters[1].name)
    }

    @Test
    fun `should emit Error state when use case returns error`() = runTest {
        // Given
        val errorMessage = "Network error occurred"
        val exception = Exception(errorMessage)
        every { mockUseCase() } returns flowOf(Result.Error(exception))

        // When
        viewModel.loadCharacters()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterListUiState.Error)
        
        val errorState = currentState as CharacterListUiState.Error
        assertEquals(errorMessage, errorState.message)
    }

    @Test
    fun `should emit Error state with unknown error when exception message is null`() = runTest {
        // Given
        val exception = Exception()
        every { mockUseCase() } returns flowOf(Result.Error(exception))

        // When
        viewModel.loadCharacters()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterListUiState.Error)
        
        val errorState = currentState as CharacterListUiState.Error
        assertEquals("Unknown error", errorState.message)
    }

    @Test
    fun `should handle empty character list correctly`() = runTest {
        // Given
        val emptyCharacterList = CharacterList(
            info = Info(count = 0, pages = 0, next = null, prev = null),
            results = emptyList()
        )
        every { mockUseCase() } returns flowOf(Result.Success(emptyCharacterList))

        // When
        viewModel.loadCharacters()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterListUiState.Success)
        
        val successState = currentState as CharacterListUiState.Success
        assertTrue(successState.characters.isEmpty())
    }

    @Test
    fun `should handle single character correctly`() = runTest {
        // Given
        val singleCharacterList = CharacterList(
            info = Info(count = 1, pages = 1, next = null, prev = null),
            results = listOf(mockCharacters[0])
        )
        every { mockUseCase() } returns flowOf(Result.Success(singleCharacterList))

        // When
        viewModel.loadCharacters()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        val currentState = viewModel.uiState.value
        assertTrue(currentState is CharacterListUiState.Success)
        
        val successState = currentState as CharacterListUiState.Success
        assertEquals(1, successState.characters.size)
        assertEquals("Rick Sanchez", successState.characters[0].name)
    }

    @Test
    fun `should handle multiple state changes correctly`() = runTest {
        // Given
        val characterList = CharacterList(
            info = Info(count = 2, pages = 1, next = null, prev = null),
            results = mockCharacters
        )
        
        // First call returns success
        every { mockUseCase() } returns flowOf(Result.Success(characterList))

        // When
        viewModel.loadCharacters()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.uiState.value is CharacterListUiState.Success)

        // Given - Second call returns error
        val exception = Exception("Second error")
        every { mockUseCase() } returns flowOf(Result.Error(exception))

        // When
        viewModel.loadCharacters()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.uiState.value is CharacterListUiState.Error)
        
        val errorState = viewModel.uiState.value as CharacterListUiState.Error
        assertEquals("Second error", errorState.message)
    }

    @Test
    fun `should handle loading state transition correctly`() = runTest {
        // Given
        every { mockUseCase() } returns flowOf(Result.Loading)

        // When
        viewModel.loadCharacters()

        // Then
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.uiState.value is CharacterListUiState.Loading)
    }
}
