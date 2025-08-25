package com.example.assignmenttask.feature.character.list.di

import com.example.assignmenttask.feature.character.list.data.repository.CharacterListRepositoryImpl
import com.example.assignmenttask.feature.character.list.domain.repository.CharacterListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterListModule {
    
    @Binds
    abstract fun bindCharacterListRepository(
        characterListRepositoryImpl: CharacterListRepositoryImpl
    ): CharacterListRepository
}


