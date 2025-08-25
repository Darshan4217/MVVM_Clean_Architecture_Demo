package com.example.assignmenttask.feature.character.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.feature.character.detail.domain.model.Character
import com.example.assignmenttask.feature.character.detail.domain.usecase.GetCharacterByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 class CharacterDetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel() {
    
    private var characterId: Int = 1
    
    private val _uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)
     val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()
    
    fun setCharacterId(id: Int) {
        characterId = id
        loadCharacter()
    }
    
     fun loadCharacter() {
        viewModelScope.launch {
            getCharacterByIdUseCase(characterId).collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> CharacterDetailUiState.Loading
                    is Result.Success -> CharacterDetailUiState.Success(result.data)
                    is Result.Error -> CharacterDetailUiState.Error(result.exception.message ?: "Unknown error")
                }
            }
        }
    }
}

sealed class CharacterDetailUiState {
    object Loading : CharacterDetailUiState()
    data class Success(val character: Character) : CharacterDetailUiState()
    data class Error(val message: String) : CharacterDetailUiState()
}
