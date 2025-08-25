package com.example.assignmenttask.feature.character.list.domain.repository

import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.feature.character.list.domain.model.CharacterList
import kotlinx.coroutines.flow.Flow

interface CharacterListRepository {
    fun getCharacters(): Flow<Result<CharacterList>>
}
