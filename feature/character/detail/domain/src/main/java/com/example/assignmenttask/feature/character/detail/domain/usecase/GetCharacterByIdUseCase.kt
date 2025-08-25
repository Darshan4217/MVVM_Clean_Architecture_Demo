package com.example.assignmenttask.feature.character.detail.domain.usecase

import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.feature.character.detail.domain.model.Character
import com.example.assignmenttask.feature.character.detail.domain.repository.CharacterDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val repository: CharacterDetailRepository
) {
    operator fun invoke(id: Int): Flow<Result<Character>> = repository.getCharacterById(id)
}
