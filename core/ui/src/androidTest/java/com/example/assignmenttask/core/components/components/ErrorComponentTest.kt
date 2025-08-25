package com.example.assignmenttask.core.components.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignmenttask.core.components.ErrorComponent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ErrorComponentTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun errorComponentShouldDisplayErrorMessage() {
        // Given
        val errorMessage = "Network error occurred"
        
        // When
        composeTestRule.setContent {
            ErrorComponent(
                message = errorMessage
            )
        }
        
        // Then
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

}



