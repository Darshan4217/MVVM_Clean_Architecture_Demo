package com.example.assignmenttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignmenttask.core.components.theme.AssignmentTaskTheme
import com.example.assignmenttask.feature.character.detail.presentation.CharacterDetailRoute
import com.example.assignmenttask.feature.character.list.presentation.CharacterListRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssignmentTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AssignmentTaskApp()
                }
            }
        }
    }
}

@Composable
fun AssignmentTaskApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "character_list"
    ) {
        composable("character_list") {
            CharacterListRoute(
                onCharacterClick = { character ->
                    navController.navigate("character_detail/${character.id}")
                }
            )
        }
        
        composable(
            route = "character_detail/{characterId}",
            arguments = listOf(
                navArgument("characterId") {
                    type = NavType.IntType
                }
            )
        ) {
            CharacterDetailRoute(navController = navController)
        }
    }
}