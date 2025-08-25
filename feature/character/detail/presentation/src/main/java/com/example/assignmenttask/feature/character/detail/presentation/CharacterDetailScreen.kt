package com.example.assignmenttask.feature.character.detail.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.assignmenttask.feature.character.detail.domain.model.Character
import com.example.assignmenttask.core.components.ErrorComponent
import com.example.assignmenttask.core.components.LoadingComponent

@Composable
fun CharacterDetailRoute(
    navController: NavController,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Get characterId from navigation arguments
    val characterId = navController.currentBackStackEntry
        ?.arguments?.getInt("characterId", 1) ?: 1

    LaunchedEffect(characterId) {
        viewModel.setCharacterId(characterId)
    }

    CharacterDetailScreen(
        uiState = uiState
    )
}

@Composable
fun CharacterDetailScreen(
    uiState: CharacterDetailUiState
) {
    when (uiState) {
        is CharacterDetailUiState.Loading -> {
            LoadingComponent()
        }

        is CharacterDetailUiState.Success -> {
            CharacterDetailContent(character = uiState.character)
        }

        is CharacterDetailUiState.Error -> {
            ErrorComponent(
                message = uiState.message
            )
        }
    }
}

@Composable
private fun CharacterDetailContent(character: Character) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Character Image
        AsyncImage(
            model = character.image,
            contentDescription = "Character image",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Character Name
        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Character Details
        CharacterInfoSection("Status", character.status)
        CharacterInfoSection("Species", character.species)
        CharacterInfoSection("Type", character.type.ifEmpty { "Unknown" })
        CharacterInfoSection("Gender", character.gender)
        CharacterInfoSection("Origin", character.origin.name)
        CharacterInfoSection("Location", character.location.name)
        CharacterInfoSection("Created", character.created)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Episodes
        Text(
            text = "Episodes (${character.episode.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        character.episode.forEach { episode ->
            Text(
                text = episode,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
            )
        }
    }
}

@Composable
private fun CharacterInfoSection(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(80.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
