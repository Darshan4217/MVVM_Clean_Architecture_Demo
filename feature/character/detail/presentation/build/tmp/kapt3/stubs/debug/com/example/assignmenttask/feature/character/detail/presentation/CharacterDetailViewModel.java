package com.example.assignmenttask.feature.character.detail.presentation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\tR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0012"}, d2 = {"Lcom/example/assignmenttask/feature/character/detail/presentation/CharacterDetailViewModel;", "Landroidx/lifecycle/ViewModel;", "getCharacterByIdUseCase", "Lcom/example/assignmenttask/feature/character/detail/domain/usecase/GetCharacterByIdUseCase;", "(Lcom/example/assignmenttask/feature/character/detail/domain/usecase/GetCharacterByIdUseCase;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/assignmenttask/feature/character/detail/presentation/CharacterDetailUiState;", "characterId", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadCharacter", "", "setCharacterId", "id", "presentation_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CharacterDetailViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.assignmenttask.feature.character.detail.domain.usecase.GetCharacterByIdUseCase getCharacterByIdUseCase = null;
    private int characterId = 1;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.assignmenttask.feature.character.detail.presentation.CharacterDetailUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.assignmenttask.feature.character.detail.presentation.CharacterDetailUiState> uiState = null;
    
    @javax.inject.Inject()
    public CharacterDetailViewModel(@org.jetbrains.annotations.NotNull()
    com.example.assignmenttask.feature.character.detail.domain.usecase.GetCharacterByIdUseCase getCharacterByIdUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.assignmenttask.feature.character.detail.presentation.CharacterDetailUiState> getUiState() {
        return null;
    }
    
    public final void setCharacterId(int id) {
    }
    
    public final void loadCharacter() {
    }
}