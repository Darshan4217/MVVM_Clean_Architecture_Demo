package com.example.assignmenttask.core.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultTest {
    
    @Test
    fun `Result Success should contain data`() {
        // Given
        val data = "test data"
        val result = Result.Success(data)
        
        // Then
        assertEquals(data, result.data)
    }
    
    @Test
    fun `Result Error should contain exception`() {
        // Given
        val exception = Exception("test error")
        val result = Result.Error(exception)
        
        // Then
        assertEquals(exception, result.exception)
    }
    
    @Test
    fun `Result Loading should be singleton`() {
        // Given
        val loading1 = Result.Loading
        val loading2 = Result.Loading
        
        // Then
        assertTrue(loading1 === loading2)
    }

}




