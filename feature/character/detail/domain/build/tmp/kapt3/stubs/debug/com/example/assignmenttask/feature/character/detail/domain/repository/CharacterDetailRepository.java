package com.example.assignmenttask.feature.character.detail.domain.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\b"}, d2 = {"Lcom/example/assignmenttask/feature/character/detail/domain/repository/CharacterDetailRepository;", "", "getCharacterById", "Lkotlinx/coroutines/flow/Flow;", "Lcom/example/assignmenttask/core/common/Result;", "Lcom/example/assignmenttask/feature/character/detail/domain/model/Character;", "id", "", "domain_debug"})
public abstract interface CharacterDetailRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.example.assignmenttask.core.common.Result<com.example.assignmenttask.feature.character.detail.domain.model.Character>> getCharacterById(int id);
}