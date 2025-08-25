package com.example.assignmenttask.feature.character.list.domain.usecase

import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.feature.character.list.domain.model.CharacterList
import com.example.assignmenttask.feature.character.list.domain.repository.CharacterListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterListRepository
) {
    operator fun invoke(): Flow<Result<CharacterList>> = repository.getCharacters()
}
