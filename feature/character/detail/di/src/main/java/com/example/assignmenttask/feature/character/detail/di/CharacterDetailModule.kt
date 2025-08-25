package com.example.assignmenttask.feature.character.detail.di

import com.example.assignmenttask.feature.character.detail.data.repository.CharacterDetailRepositoryImpl
import com.example.assignmenttask.feature.character.detail.domain.repository.CharacterDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterDetailModule {
    
    @Binds
    abstract fun bindCharacterDetailRepository(
        characterDetailRepositoryImpl: CharacterDetailRepositoryImpl
    ): CharacterDetailRepository
}
