package com.example.assignmenttask.feature.character.detail.data.model

data class CharacterDetailData(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationData,
    val location: LocationData,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

data class LocationData(
    val name: String,
    val url: String
)
