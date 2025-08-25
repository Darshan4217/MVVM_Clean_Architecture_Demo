package com.example.assignmenttask.feature.character.list.data.mapper

import com.example.assignmenttask.feature.character.list.data.model.CharacterListData
import com.example.assignmenttask.feature.character.list.data.model.CharacterListResponseData
import com.example.assignmenttask.feature.character.list.data.model.InfoData
import com.example.assignmenttask.feature.character.list.data.model.LocationData
import com.example.assignmenttask.feature.character.list.domain.model.Character
import com.example.assignmenttask.feature.character.list.domain.model.CharacterList
import com.example.assignmenttask.feature.character.list.domain.model.Info
import com.example.assignmenttask.feature.character.list.domain.model.Location
import com.example.assignmenttask.core.network.model.CharacterDto
import com.example.assignmenttask.core.network.model.CharacterListDto
import com.example.assignmenttask.core.network.model.InfoDto
import com.example.assignmenttask.core.network.model.LocationDto

// Network DTO to Data Model mappings
fun CharacterDto.toCharacterListData(): CharacterListData {
    return CharacterListData(
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

fun CharacterListDto.toCharacterListResponseData(): CharacterListResponseData {
    return CharacterListResponseData(
        info = info.toInfoData(),
        results = results.map { it.toCharacterListData() }
    )
}

fun InfoDto.toInfoData(): InfoData {
    return InfoData(
        count = count,
        pages = pages,
        next = next,
        prev = prev
    )
}

// Data Model to Domain Model mappings
fun CharacterListData.toCharacter(): Character {
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

fun CharacterListResponseData.toCharacterList(): CharacterList {
    return CharacterList(
        info = info.toInfo(),
        results = results.map { it.toCharacter() }
    )
}

fun InfoData.toInfo(): Info {
    return Info(
        count = count,
        pages = pages,
        next = next,
        prev = prev
    )
}
