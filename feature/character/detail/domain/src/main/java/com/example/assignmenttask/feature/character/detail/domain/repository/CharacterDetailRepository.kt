package com.example.assignmenttask.feature.character.detail.domain.repository

import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.feature.character.detail.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterDetailRepository {
    fun getCharacterById(id: Int): Flow<Result<Character>>
}
