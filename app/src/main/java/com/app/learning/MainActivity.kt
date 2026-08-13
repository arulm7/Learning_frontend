package com.app.learning

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import android.os.Bundle
import com.app.learning.model.Student
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.learning.ui.theme.LearningTheme
import com.app.learning.viewmodel.StudentViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            LearningTheme {
                StudentScreen()
            }
        }
    }
}

@Composable
fun StudentScreen(
    viewModel: StudentViewModel = viewModel()
) {

    val students by viewModel.students.collectAsState()

    var name by remember {
        mutableStateOf("")
    }

    var course by remember {
        mutableStateOf("")
    }

    var editId by remember {
        mutableStateOf<Int?>(null)
    }

    LaunchedEffect(Unit) {
        viewModel.loadStudents()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text("Student Management")

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Student Name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = course,
            onValueChange = {
                course = it
            },
            label = {
                Text("Course")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )
        Button(
            onClick = {

                if (editId != null) {

                    viewModel.updateStudent(
                        id = editId!!,
                        name = name,
                        course = course
                    )

                    editId = null

                } else {

                    viewModel.createStudent(
                        name = name,
                        course = course
                    )
                }

                name = ""
                course = ""

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (editId != null)
                    "Update Student"
                else
                    "Add Student"
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            items(students) { student ->

                StudentCard(
                    student = student,
                    onEdit = {
                        name = student.name
                        course = student.course
                        editId = student.id
                    },
                    onDelete = {
                        viewModel.deleteStudent(student.id)
                    }
                )
            }
        }
    }
}

@Composable
fun StudentCard(
    student: Student,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "ID: ${student.id}"
            )

            Text(
                text = "Name: ${student.name}"
            )

            Text(
                text = "Course: ${student.course}"
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )


            Button(
                onClick = onEdit,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit")
            }

            Button(
                onClick = onDelete,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete")
            }
        }
    }
}