package com.example.assignmenttask.feature.character.list.data.repository

import app.cash.turbine.test
import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.core.network.api.CharacterApi
import com.example.assignmenttask.core.network.model.CharacterDto
import com.example.assignmenttask.core.network.model.CharacterListDto
import com.example.assignmenttask.core.network.model.InfoDto
import com.example.assignmenttask.core.network.model.LocationDto
import com.example.assignmenttask.feature.character.list.domain.model.Character
import com.example.assignmenttask.feature.character.list.domain.model.CharacterList
import com.example.assignmenttask.feature.character.list.domain.model.Info
import com.example.assignmenttask.feature.character.list.domain.model.Location
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.net.SocketTimeoutException

class CharacterListRepositoryImplTest {

    private lateinit var repository: CharacterListRepositoryImpl
    private lateinit var mockApi: CharacterApi

    @Before
    fun setup() {
        mockApi = mockk()
        repository = CharacterListRepositoryImpl(mockApi)
    }

    @Test
    fun `getCharacters should emit Loading then Success when API call succeeds`() = runTest {
        // Given
        val mockCharacterDto = CharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = LocationDto("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            location = LocationDto("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        )

        val mockInfoDto = InfoDto(
            count = 1,
            pages = 1,
            next = null,
            prev = null
        )

        val mockCharacterListDto = CharacterListDto(
            info = mockInfoDto,
            results = listOf(mockCharacterDto)
        )

        val expectedCharacter = Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Location("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            location = Location("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        )

        val expectedCharacterList = CharacterList(
            info = Info(
                count = 1,
                pages = 1,
                next = null,
                prev = null
            ),
            results = listOf(expectedCharacter)
        )

        coEvery { mockApi.getCharacters() } returns mockCharacterListDto

        // When & Then
        repository.getCharacters().test {
            // First emission should be Loading
            val loadingResult = awaitItem()
            assertTrue("First emission should be Loading", loadingResult is Result.Loading)

            // Second emission should be Success with transformed data
            val successResult = awaitItem()
            assertTrue("Second emission should be Success", successResult is Result.Success)
            assertEquals("Character list should match expected", expectedCharacterList, (successResult as Result.Success).data)

            awaitComplete()
        }
    }

    @Test
    fun `getCharacters should emit Loading then Error when API call fails with IOException`() = runTest {
        // Given
        val exception = IOException("Network error")
        coEvery { mockApi.getCharacters() } throws exception

        // When & Then
        repository.getCharacters().test {
            // First emission should be Loading
            val loadingResult = awaitItem()
            assertTrue("First emission should be Loading", loadingResult is Result.Loading)

            // Second emission should be Error
            val errorResult = awaitItem()
            assertTrue("Second emission should be Error", errorResult is Result.Error)
            assertEquals("Exception should match", exception, (errorResult as Result.Error).exception)

            awaitComplete()
        }
    }

    @Test
    fun `getCharacters should emit Loading then Error when API call fails with SocketTimeoutException`() = runTest {
        // Given
        val exception = SocketTimeoutException("Request timeout")
        coEvery { mockApi.getCharacters() } throws exception

        // When & Then
        repository.getCharacters().test {
            // First emission should be Loading
            val loadingResult = awaitItem()
            assertTrue("First emission should be Loading", loadingResult is Result.Loading)

            // Second emission should be Error
            val errorResult = awaitItem()
            assertTrue("Second emission should be Error", errorResult is Result.Error)
            assertEquals("Exception should match", exception, (errorResult as Result.Error).exception)

            awaitComplete()
        }
    }

    @Test
    fun `getCharacters should emit Loading then Error when API call fails with RuntimeException`() = runTest {
        // Given
        val exception = RuntimeException("Unexpected error")
        coEvery { mockApi.getCharacters() } throws exception

        // When & Then
        repository.getCharacters().test {
            // First emission should be Loading
            val loadingResult = awaitItem()
            assertTrue("First emission should be Loading", loadingResult is Result.Loading)

            // Second emission should be Error
            val errorResult = awaitItem()
            assertTrue("Second emission should be Error", errorResult is Result.Error)
            assertEquals("Exception should match", exception, (errorResult as Result.Error).exception)

            awaitComplete()
        }
    }

    @Test
    fun `getCharacters should handle empty character list correctly`() = runTest {
        // Given
        val mockInfoDto = InfoDto(
            count = 0,
            pages = 0,
            next = null,
            prev = null
        )

        val mockCharacterListDto = CharacterListDto(
            info = mockInfoDto,
            results = emptyList()
        )

        val expectedCharacterList = CharacterList(
            info = Info(
                count = 0,
                pages = 0,
                next = null,
                prev = null
            ),
            results = emptyList()
        )

        coEvery { mockApi.getCharacters() } returns mockCharacterListDto

        // When & Then
        repository.getCharacters().test {
            // First emission should be Loading
            val loadingResult = awaitItem()
            assertTrue("First emission should be Loading", loadingResult is Result.Loading)

            // Second emission should be Success with empty list
            val successResult = awaitItem()
            assertTrue("Second emission should be Success", successResult is Result.Success)
            assertEquals("Character list should be empty", expectedCharacterList, (successResult as Result.Success).data)
            assertTrue("Results should be empty", (successResult.data).results.isEmpty())

            awaitComplete()
        }
    }

    @Test
    fun `getCharacters should handle multiple characters correctly`() = runTest {
        // Given
        val mockCharacterDto1 = CharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = LocationDto("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            location = LocationDto("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        )

        val mockCharacterDto2 = CharacterDto(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = LocationDto("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            location = LocationDto("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20"),
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/2",
            created = "2017-11-04T18:50:21.651Z"
        )

        val mockInfoDto = InfoDto(
            count = 2,
            pages = 1,
            next = null,
            prev = null
        )

        val mockCharacterListDto = CharacterListDto(
            info = mockInfoDto,
            results = listOf(mockCharacterDto1, mockCharacterDto2)
        )

        coEvery { mockApi.getCharacters() } returns mockCharacterListDto

        // When & Then
        repository.getCharacters().test {
            // First emission should be Loading
            val loadingResult = awaitItem()
            assertTrue("First emission should be Loading", loadingResult is Result.Loading)

            // Second emission should be Success with multiple characters
            val successResult = awaitItem()
            assertTrue("Second emission should be Success", successResult is Result.Success)
            
            val characterList = (successResult as Result.Success).data
            assertEquals("Should have 2 characters", 2, characterList.results.size)
            assertEquals("First character name should match", "Rick Sanchez", characterList.results[0].name)
            assertEquals("Second character name should match", "Morty Smith", characterList.results[1].name)
            assertEquals("Info count should match", 2, characterList.info.count)

            awaitComplete()
        }
    }

    @Test
    fun `getCharacters should handle pagination info correctly`() = runTest {
        // Given
        val mockCharacterDto = CharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = LocationDto("Earth (C-137)", "https://rickandmortyapi.com/api/location/1"),
            location = LocationDto("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        )

        val mockInfoDto = InfoDto(
            count = 826,
            pages = 42,
            next = "https://rickandmortyapi.com/api/character?page=2",
            prev = null
        )

        val mockCharacterListDto = CharacterListDto(
            info = mockInfoDto,
            results = listOf(mockCharacterDto)
        )

        val expectedInfo = Info(
            count = 826,
            pages = 42,
            next = "https://rickandmortyapi.com/api/character?page=2",
            prev = null
        )

        coEvery { mockApi.getCharacters() } returns mockCharacterListDto

        // When & Then
        repository.getCharacters().test {
            // First emission should be Loading
            val loadingResult = awaitItem()
            assertTrue("First emission should be Loading", loadingResult is Result.Loading)

            // Second emission should be Success with correct pagination info
            val successResult = awaitItem()
            assertTrue("Second emission should be Success", successResult is Result.Success)
            
            val characterList = (successResult as Result.Success).data
            assertEquals("Info should match expected", expectedInfo, characterList.info)
            assertEquals("Count should be 826", 826, characterList.info.count)
            assertEquals("Pages should be 42", 42, characterList.info.pages)
            assertEquals("Next URL should match", "https://rickandmortyapi.com/api/character?page=2", characterList.info.next)
            assertEquals("Prev should be null", null, characterList.info.prev)

            awaitComplete()
        }
    }

    @Test
    fun `getCharacters should map character fields correctly`() = runTest {
        // Given - Character with all fields populated
        val mockCharacterDto = CharacterDto(
            id = 42,
            name = "Summer Smith",
            status = "Alive",
            species = "Human",
            type = "Teenager",
            gender = "Female",
            origin = LocationDto("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20"),
            location = LocationDto("Earth (Replacement Dimension)", "https://rickandmortyapi.com/api/location/20"),
            image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
                "https://rickandmortyapi.com/api/episode/3"
            ),
            url = "https://rickandmortyapi.com/api/character/3",
            created = "2017-11-04T18:50:21.651Z"
        )

        val mockInfoDto = InfoDto(count = 1, pages = 1, next = null, prev = null)
        val mockCharacterListDto = CharacterListDto(info = mockInfoDto, results = listOf(mockCharacterDto))

        coEvery { mockApi.getCharacters() } returns mockCharacterListDto

        // When & Then
        repository.getCharacters().test {
            // Skip Loading
            awaitItem()

            // Verify Success with correctly mapped fields
            val successResult = awaitItem()
            val character = (successResult as Result.Success).data.results[0]
            
            assertEquals("ID should be mapped", 42, character.id)
            assertEquals("Name should be mapped", "Summer Smith", character.name)
            assertEquals("Status should be mapped", "Alive", character.status)
            assertEquals("Species should be mapped", "Human", character.species)
            assertEquals("Type should be mapped", "Teenager", character.type)
            assertEquals("Gender should be mapped", "Female", character.gender)
            assertEquals("Origin name should be mapped", "Earth (Replacement Dimension)", character.origin.name)
            assertEquals("Origin URL should be mapped", "https://rickandmortyapi.com/api/location/20", character.origin.url)
            assertEquals("Location name should be mapped", "Earth (Replacement Dimension)", character.location.name)
            assertEquals("Location URL should be mapped", "https://rickandmortyapi.com/api/location/20", character.location.url)
            assertEquals("Image should be mapped", "https://rickandmortyapi.com/api/character/avatar/3.jpeg", character.image)
            assertEquals("Episodes should be mapped", 3, character.episode.size)
            assertEquals("URL should be mapped", "https://rickandmortyapi.com/api/character/3", character.url)
            assertEquals("Created should be mapped", "2017-11-04T18:50:21.651Z", character.created)

            awaitComplete()
        }
    }
}

