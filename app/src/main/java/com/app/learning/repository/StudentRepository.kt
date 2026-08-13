package com.app.learning.repository

import com.app.learning.api.RetrofitClient
import com.app.learning.model.Student
import com.app.learning.model.StudentRequest

class StudentRepository {

    private val api = RetrofitClient.api

    suspend fun getStudents(): List<Student> {

        val response = api.getStudents()

        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }

        throw Exception("Failed to load students")
    }

    suspend fun createStudent(
        student: StudentRequest
    ): Student {

        val response = api.createStudent(student)

        if (response.isSuccessful) {
            return response.body()!!
        }

        throw Exception("Failed to create student")
    }

    suspend fun updateStudent(
        id: Int,
        student: StudentRequest
    ): Student {

        val response = api.updateStudent(id, student)

        if (response.isSuccessful) {
            return response.body()!!
        }

        throw Exception("Failed to update student")
    }

    suspend fun deleteStudent(id: Int) {

        val response = api.deleteStudent(id)

        if (response.isSuccessful) {
            return
        }

        throw Exception("Failed to delete student")
    }
}