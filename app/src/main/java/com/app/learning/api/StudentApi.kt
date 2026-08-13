package com.app.learning.api

//import com.app.learning.Student
import com.app.learning.model.Student
import com.app.learning.model.StudentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentApi {

    @GET("students")
    suspend fun getStudents(): Response<List<Student>>

    @POST("student")
    suspend fun createStudent(
        @Body student: StudentRequest
    ): Response<Student>

    @DELETE("/students/{id}")
    suspend fun deleteStudent(
        @Path("id") id: Int
    ): Response<Void>

    @PUT("/students/{id}")
    suspend fun updateStudent(
        @Path("id") id: Int,
        @Body student: StudentRequest
    ): Response<Student>
}