package com.example.assignmenttask.core.network.api

import com.example.assignmenttask.core.network.model.CharacterDto
import com.example.assignmenttask.core.network.model.CharacterListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApi {
    
    @GET("character")
    suspend fun getCharacters(): CharacterListDto
    
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterDto
}
