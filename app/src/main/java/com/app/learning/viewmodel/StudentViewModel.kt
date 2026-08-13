package com.app.learning.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.learning.model.StudentRequest
import com.app.learning.model.StudentUiState
import com.app.learning.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {

    private val repository = StudentRepository()

    private val _uiState =
        MutableStateFlow(StudentUiState())

    val uiState: StateFlow<StudentUiState> = _uiState

    fun loadStudents() {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                kotlinx.coroutines.delay(3000)
//

                val students = repository.getStudents()

                _uiState.value = _uiState.value.copy(
                    students = students,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }


    fun createStudent(
        name: String,
        course: String
    ) {

        viewModelScope.launch {

            try {

                val student = StudentRequest(
                    name = name,
                    course = course
                )

                repository.createStudent(student)

                loadStudents()

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
        }
    }


    fun updateStudent(
        id: Int,
        name: String,
        course: String
    ) {

        viewModelScope.launch {

            try {

                val student = StudentRequest(
                    name = name,
                    course = course
                )

                repository.updateStudent(
                    id,
                    student
                )

                loadStudents()

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
        }
    }


    fun deleteStudent(id: Int) {

        viewModelScope.launch {

            try {

                repository.deleteStudent(id)

                loadStudents()

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    error = e.message
                )
            }
        }
    }
}