package com.example.assignmenttask.feature.character.detail.data.mapper

import com.example.assignmenttask.feature.character.detail.data.model.CharacterDetailData
import com.example.assignmenttask.feature.character.detail.data.model.LocationData
import com.example.assignmenttask.feature.character.detail.domain.model.Character
import com.example.assignmenttask.feature.character.detail.domain.model.Location
import com.example.assignmenttask.core.network.model.CharacterDto
import com.example.assignmenttask.core.network.model.LocationDto

// Network DTO to Data Model mappings
fun CharacterDto.toCharacterDetailData(): CharacterDetailData {
    return CharacterDetailData(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.toLocationData(),
        location = location.toLocationData(),
        image = image,
        episode = episode,
        url = url,
        created = created
    )
}

fun LocationDto.toLocationData(): LocationData {
    return LocationData(
        name = name,
        url = url
    )
}

// Data Model to Domain Model mappings
fun CharacterDetailData.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.toLocation(),
        location = location.toLocation(),
        image = image,
        episode = episode,
        url = url,
        created = created
    )
}

fun LocationData.toLocation(): Location {
    return Location(
        name = name,
        url = url
    )
}
