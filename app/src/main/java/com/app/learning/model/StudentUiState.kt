package com.app.learning.model

data class StudentUiState(
    val students : List<Student> = emptyList(),
    val isLoading : Boolean = false,
    val error: String? =null

)
