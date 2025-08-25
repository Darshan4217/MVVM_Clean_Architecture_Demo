package com.example.assignmenttask.feature.character.detail.data.repository

import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.core.network.api.CharacterApi
import com.example.assignmenttask.core.network.model.CharacterDto
import com.example.assignmenttask.core.network.model.LocationDto
import com.example.assignmenttask.feature.character.detail.data.mapper.toCharacterDetailData
import com.example.assignmenttask.feature.character.detail.data.mapper.toCharacter
import com.example.assignmenttask.feature.character.detail.domain.model.Character
import com.example.assignmenttask.feature.character.detail.domain.model.Location
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class CharacterDetailRepositoryImplTest {

    private lateinit var repository: CharacterDetailRepositoryImpl
    private lateinit var mockApi: CharacterApi
    private val testDispatcher = StandardTestDispatcher()

    private val mockCharacterDto = CharacterDto(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "Genius",
        gender = "Male",
        origin = LocationDto("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
        location = LocationDto("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20"),
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        episode = listOf("https://rickandmortyapi.com/api/episode/1"),
        url = "https://rickandmortyapi.com/api/character/1",
        created = "2017-11-04T18:48:46.250Z"
    )

    private val expectedCharacterData = mockCharacterDto.toCharacterDetailData()
    private val expectedCharacter = expectedCharacterData.toCharacter()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockApi = mockk()
        repository = CharacterDetailRepositoryImpl(mockApi)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `getCharacterById should emit Loading then Success when API call succeeds`() = runTest {
        // Given
        val characterId = 1
        coEvery { mockApi.getCharacterById(characterId) } returns mockCharacterDto

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val states = mutableListOf<Result<Character>>()
        result.collect { states.add(it) }

        assertEquals(2, states.size)
        assertTrue(states[0] is Result.Loading)
        assertTrue(states[1] is Result.Success)
        
        val successState = states[1] as Result.Success
        assertEquals(expectedCharacter, successState.data)
    }

    @Test
    fun `getCharacterById should emit Loading then Success with correct character data`() = runTest {
        // Given
        val characterId = 1
        coEvery { mockApi.getCharacterById(characterId) } returns mockCharacterDto

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val successState = result.first { it is Result.Success } as Result.Success
        val character = successState.data

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
    fun `getCharacterById should emit Loading then Success with correct location data`() = runTest {
        // Given
        val characterId = 1
        coEvery { mockApi.getCharacterById(characterId) } returns mockCharacterDto

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val successState = result.first { it is Result.Success } as Result.Success
        val character = successState.data

        // Check origin location
        assertEquals("Earth (C-137)", character.origin.name)
        assertEquals("https://rickandmortyapi.com/api/location/1", character.origin.url)

        // Check current location
        assertEquals("Earth (Replacement Dimension)", character.location.name)
        assertEquals("https://rickandmortyapi.com/api/location/20", character.location.url)
    }

    @Test
    fun `getCharacterById should emit Loading then Success with empty type`() = runTest {
        // Given
        val characterWithEmptyType = mockCharacterDto.copy(type = "")
        val characterId = 1
        coEvery { mockApi.getCharacterById(characterId) } returns characterWithEmptyType

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val successState = result.first { it is Result.Success } as Result.Success
        val character = successState.data

        assertEquals("", character.type)
    }

    @Test
    fun `getCharacterById should emit Loading then Success with multiple episodes`() = runTest {
        // Given
        val episodes = listOf(
            "https://rickandmortyapi.com/api/episode/1",
            "https://rickandmortyapi.com/api/episode/2",
            "https://rickandmortyapi.com/api/episode/3"
        )
        val characterWithMultipleEpisodes = mockCharacterDto.copy(episode = episodes)
        val characterId = 1
        coEvery { mockApi.getCharacterById(characterId) } returns characterWithMultipleEpisodes

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val successState = result.first { it is Result.Success } as Result.Success
        val character = successState.data

        assertEquals(3, character.episode.size)
        assertEquals("https://rickandmortyapi.com/api/episode/1", character.episode[0])
        assertEquals("https://rickandmortyapi.com/api/episode/2", character.episode[1])
        assertEquals("https://rickandmortyapi.com/api/episode/3", character.episode[2])
    }

    @Test
    fun `getCharacterById should emit Loading then Error when API call throws exception`() = runTest {
        // Given
        val characterId = 1
        val exception = Exception("Network error occurred")
        coEvery { mockApi.getCharacterById(characterId) } throws exception

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val states = mutableListOf<Result<Character>>()
        result.collect { states.add(it) }

        assertEquals(2, states.size)
        assertTrue(states[0] is Result.Loading)
        assertTrue(states[1] is Result.Error)
        
        val errorState = states[1] as Result.Error
        assertEquals(exception, errorState.exception)
    }

    @Test
    fun `getCharacterById should emit Loading then Error with different exception types`() = runTest {
        // Given
        val characterId = 1
        val runtimeException = RuntimeException("Runtime error")
        coEvery { mockApi.getCharacterById(characterId) } throws runtimeException

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val errorState = result.first { it is Result.Error } as Result.Error
        assertEquals(runtimeException, errorState.exception)
    }

    @Test
    fun `getCharacterById should handle different character IDs correctly`() = runTest {
        // Given
        val characterId1 = 1
        val characterId2 = 2
        val character2 = mockCharacterDto.copy(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human"
        )
        
        coEvery { mockApi.getCharacterById(characterId1) } returns mockCharacterDto
        coEvery { mockApi.getCharacterById(characterId2) } returns character2

        // When & Then - First character
        val result1 = repository.getCharacterById(characterId1)
        val successState1 = result1.first { it is Result.Success } as Result.Success
        assertEquals(1, successState1.data.id)
        assertEquals("Rick Sanchez", successState1.data.name)

        // When & Then - Second character
        val result2 = repository.getCharacterById(characterId2)
        val successState2 = result2.first { it is Result.Success } as Result.Success
        assertEquals(2, successState2.data.id)
        assertEquals("Morty Smith", successState2.data.name)
    }

    @Test
    fun `getCharacterById should handle character with null episode list`() = runTest {
        // Given
        val characterWithNullEpisodes = mockCharacterDto.copy(episode = emptyList())
        val characterId = 1
        coEvery { mockApi.getCharacterById(characterId) } returns characterWithNullEpisodes

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val successState = result.first { it is Result.Success } as Result.Success
        val character = successState.data

        assertTrue(character.episode.isEmpty())
    }

    @Test
    fun `getCharacterById should handle character with special characters in name`() = runTest {
        // Given
        val characterWithSpecialName = mockCharacterDto.copy(name = "Rick & Morty's Dad")
        val characterId = 1
        coEvery { mockApi.getCharacterById(characterId) } returns characterWithSpecialName

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val successState = result.first { it is Result.Success } as Result.Success
        val character = successState.data

        assertEquals("Rick & Morty's Dad", character.name)
    }

    @Test
    fun `getCharacterById should handle character with very long strings`() = runTest {
        // Given
        val longString = "A".repeat(1000)
        val characterWithLongStrings = mockCharacterDto.copy(
            name = longString,
            type = longString,
            url = longString
        )
        val characterId = 1
        coEvery { mockApi.getCharacterById(characterId) } returns characterWithLongStrings

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val successState = result.first { it is Result.Success } as Result.Success
        val character = successState.data

        assertEquals(longString, character.name)
        assertEquals(longString, character.type)
        assertEquals(longString, character.url)
    }

    @Test
    fun `getCharacterById should emit Loading state immediately`() = runTest {
        // Given
        val characterId = 1
        coEvery { mockApi.getCharacterById(characterId) } returns mockCharacterDto

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val firstState = result.first()
        assertTrue(firstState is Result.Loading)
    }

    @Test
    fun `getCharacterById should complete flow after emitting final state`() = runTest {
        // Given
        val characterId = 1
        coEvery { mockApi.getCharacterById(characterId) } returns mockCharacterDto

        // When
        val result = repository.getCharacterById(characterId)

        // Then
        val states = mutableListOf<Result<Character>>()
        result.collect { states.add(it) }

        assertEquals(2, states.size)
        assertTrue(states[0] is Result.Loading)
        assertTrue(states[1] is Result.Success)
    }
}

