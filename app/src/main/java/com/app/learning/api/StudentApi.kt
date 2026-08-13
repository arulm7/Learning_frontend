package com.app.learning.api

import com.app.learning.Student
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StudentApi {

    @GET("students")
    suspend fun getStudents(): Response<List<Student>>

    @POST("student")
    suspend fun createStudent(
        @Body student: Student
    ): Response<Student>
}