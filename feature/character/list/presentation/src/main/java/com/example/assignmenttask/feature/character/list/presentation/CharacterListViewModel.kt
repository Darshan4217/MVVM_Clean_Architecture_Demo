package com.example.assignmenttask.feature.character.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttask.core.common.Result
import com.example.assignmenttask.feature.character.list.domain.model.Character
import com.example.assignmenttask.feature.character.list.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
 class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
    
        private val _uiState = MutableStateFlow<CharacterListUiState>(CharacterListUiState.Loading)
     val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()
    
    init {
        loadCharacters()
    }
    
      fun loadCharacters() {
        viewModelScope.launch {
            getCharactersUseCase().collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> CharacterListUiState.Loading
                    is Result.Success -> CharacterListUiState.Success(result.data.results)
                    is Result.Error -> CharacterListUiState.Error(result.exception.message ?: "Unknown error")
                }
            }
        }
    }

}

sealed class CharacterListUiState {
    object Loading : CharacterListUiState()
    data class Success(val characters: List<Character>) : CharacterListUiState()
    data class Error(val message: String) : CharacterListUiState()
}
