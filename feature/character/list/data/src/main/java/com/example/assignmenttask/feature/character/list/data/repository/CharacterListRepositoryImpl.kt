package com.example.assignmenttask.feature.character.list.data.repository

import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.feature.character.list.domain.model.CharacterList
import com.example.assignmenttask.feature.character.list.domain.repository.CharacterListRepository
import com.example.assignmenttask.core.network.api.CharacterApi
import com.example.assignmenttask.feature.character.list.data.mapper.toCharacterListResponseData
import com.example.assignmenttask.feature.character.list.data.mapper.toCharacterList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterListRepositoryImpl @Inject constructor(
    private val api: CharacterApi
) : CharacterListRepository {
    
    override fun getCharacters(): Flow<Result<CharacterList>> = flow {
        emit(Result.Loading)
        val charactersDto = api.getCharacters()
        val charactersData = charactersDto.toCharacterListResponseData()
        val characters = charactersData.toCharacterList()
        emit(Result.Success(characters))
    }.catch { e ->
        emit(Result.Error(e as Exception))
    }
}
