package com.example.assignmenttask.feature.character.detail.data.repository

import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.feature.character.detail.domain.model.Character
import com.example.assignmenttask.feature.character.detail.domain.repository.CharacterDetailRepository
import com.example.assignmenttask.core.network.api.CharacterApi
import com.example.assignmenttask.feature.character.detail.data.mapper.toCharacterDetailData
import com.example.assignmenttask.feature.character.detail.data.mapper.toCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterDetailRepositoryImpl @Inject constructor(
    private val api: CharacterApi
) : CharacterDetailRepository {
    
    override fun getCharacterById(id: Int): Flow<Result<Character>> = flow {
            emit(Result.Loading)
            val characterDto = api.getCharacterById(id)
            val characterData = characterDto.toCharacterDetailData()
            val character = characterData.toCharacter()
            emit(Result.Success(character))
        }.catch { e ->
        emit(Result.Error(e as Exception))
    }
}

