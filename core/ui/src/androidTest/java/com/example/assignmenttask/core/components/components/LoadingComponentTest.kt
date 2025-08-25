package com.example.assignmenttask.core.components.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignmenttask.core.components.LoadingComponent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingComponentTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun loadingComponentShouldDisplayCircularProgressIndicator() {
        // When
        composeTestRule.setContent {
            LoadingComponent()
        }

        composeTestRule
            .onNodeWithTag("loading_indicator")
            .assertExists()
            .assertIsDisplayed()
    }
}



