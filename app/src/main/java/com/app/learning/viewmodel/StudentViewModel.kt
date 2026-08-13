package com.app.learning.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.learning.model.Student
import com.app.learning.model.StudentRequest
import com.app.learning.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadStudents() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.api.getStudents()
                if (response.isSuccessful) {
                    _students.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateStudent(id:Int,name :String,course :String){
        viewModelScope.launch {
            val student = StudentRequest(
                name = name,
                course = course
            )
            try {
                val response = RetrofitClient.api.updateStudent(id, student)
                if (response.isSuccessful) {
                    loadStudents()
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun deleteStudent(id:Int){
        viewModelScope.launch {
            val response = RetrofitClient.api.deleteStudent(id)
            if(response.isSuccessful){
                println("Deleted")
                loadStudents()
            }
            else{
                println("${response.body()}")
            }
        }
    }

    fun createStudent(name:String,course:String){
        viewModelScope.launch {
            val studentRequest = StudentRequest(
                name = name,
                course=course
            )
            val response = RetrofitClient.api.createStudent(studentRequest)

            if (response.isSuccessful){
                println("Student${response.body()}")
                loadStudents()
            }
            else{
                println("${response.code()}")
            }
        }
    }

}