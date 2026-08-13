package com.app.learning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.learning.model.Student
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen(
    viewModel: StudentViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val students = uiState.students

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


    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = "Student Manager",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Manage your students",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },

                navigationIcon = {

                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "Students",
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                )
            )
        }

    ) { innerPadding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)

        ) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )


            /*
             * FORM CARD
             */

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                )

            ) {

                Column(

                    modifier = Modifier.padding(20.dp)

                ) {

                    Text(
                        text =
                            if (editId != null)
                                "Edit Student"
                            else
                                "Add Student",

                        style =
                            MaterialTheme.typography.titleLarge,

                        fontWeight =
                            FontWeight.Bold
                    )


                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )


                    OutlinedTextField(

                        value = name,

                        onValueChange = {
                            name = it
                        },

                        label = {
                            Text("Student Name")
                        },

                        leadingIcon = {

                            Icon(
                                imageVector =
                                    Icons.Default.School,
                                contentDescription = null
                            )
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        singleLine = true,

                        shape =
                            RoundedCornerShape(14.dp)
                    )


                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )


                    OutlinedTextField(

                        value = course,

                        onValueChange = {
                            course = it
                        },

                        label = {
                            Text("Course")
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        singleLine = true,

                        shape =
                            RoundedCornerShape(14.dp)
                    )


                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )


                    Button(

                        onClick = {

                            if (
                                name.isBlank() ||
                                course.isBlank()
                            ) {
                                return@Button
                            }


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

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(14.dp)
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Add,
                            contentDescription = null
                        )

                        Spacer(
                            modifier =
                                Modifier.size(8.dp)
                        )

                        Text(

                            text =
                                if (editId != null)
                                    "Update Student"
                                else
                                    "Add Student"
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            /*
             * STUDENT HEADER
             */

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(

                    text = "Students",

                    style =
                        MaterialTheme.typography.titleLarge,

                    fontWeight =
                        FontWeight.Bold
                )


                Card(

                    shape =
                        RoundedCornerShape(50.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.primaryContainer
                        )
                ) {

                    Text(

                        text =
                            students.size.toString(),

                        modifier =
                            Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 6.dp
                            ),

                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            /*
             * LOADING
             */

            if (uiState.isLoading) {

                Column(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),

                    horizontalAlignment =
                        Alignment.CenterHorizontally,

                    verticalArrangement =
                        Arrangement.Center

                ) {

                    CircularProgressIndicator()

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    Text(
                        text = "Loading students..."
                    )
                }


            }


            /*
             * EMPTY
             */

            else if (students.isEmpty()) {

                Column(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),

                    horizontalAlignment =
                        Alignment.CenterHorizontally,

                    verticalArrangement =
                        Arrangement.Center

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.School,

                        contentDescription = null,

                        modifier =
                            Modifier.size(64.dp),

                        tint =
                            MaterialTheme.colorScheme.primary
                    )

                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )

                    Text(

                        text =
                            "No students yet",

                        style =
                            MaterialTheme.typography.titleMedium,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Text(
                        text =
                            "Add your first student above."
                    )
                }


            }


            /*
             * STUDENT LIST
             */

            else {

                LazyColumn(

                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),

                    verticalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    items(

                        items = students,

                        key = {
                            it.id
                        }

                    ) { student ->

                        StudentCard(

                            student = student,

                            onEdit = {

                                name =
                                    student.name

                                course =
                                    student.course

                                editId =
                                    student.id
                            },

                            onDelete = {

                                viewModel.deleteStudent(
                                    student.id
                                )
                            }
                        )
                    }
                }
            }


            /*
             * ERROR
             */

            uiState.error?.let { error ->

                Text(

                    text = error,

                    color = Color.Red,

                    modifier =
                        Modifier.padding(
                            vertical = 8.dp
                        )
                )
            }
        }
    }
}


/*
 * STUDENT CARD
 */

@Composable
fun StudentCard(

    student: Student,

    onEdit: () -> Unit,

    onDelete: () -> Unit

) {

    Card(

        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(18.dp),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
    ) {

        Column(

            modifier =
                Modifier.padding(16.dp)
        ) {

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                /*
                 * STUDENT ICON
                 */

                Card(

                    shape =
                        RoundedCornerShape(14.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.primaryContainer
                        )
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.School,

                        contentDescription = null,

                        modifier =
                            Modifier
                                .padding(12.dp)
                                .size(28.dp),

                        tint =
                            MaterialTheme.colorScheme.primary
                    )
                }


                Spacer(
                    modifier =
                        Modifier.size(14.dp)
                )


                /*
                 * STUDENT DETAILS
                 */

                Column(

                    modifier =
                        Modifier.weight(1f)
                ) {

                    Text(

                        text =
                            student.name,

                        style =
                            MaterialTheme.typography.titleMedium,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Text(

                        text =
                            student.course,

                        style =
                            MaterialTheme.typography.bodyMedium
                    )

                    Text(

                        text =
                            "Student ID: ${student.id}",

                        style =
                            MaterialTheme.typography.labelSmall
                    )
                }
            }


            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )


            /*
             * ACTIONS
             */

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                OutlinedButton(

                    onClick = onEdit,

                    modifier =
                        Modifier.weight(1f),

                    shape =
                        RoundedCornerShape(12.dp)
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Edit,

                        contentDescription =
                            "Edit"
                    )

                    Spacer(
                        modifier =
                            Modifier.size(6.dp)
                    )

                    Text("Edit")
                }


                OutlinedButton(

                    onClick = onDelete,

                    modifier =
                        Modifier.weight(1f),

                    shape =
                        RoundedCornerShape(12.dp)
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Delete,

                        contentDescription =
                            "Delete"
                    )

                    Spacer(
                        modifier =
                            Modifier.size(6.dp)
                    )

                    Text("Delete")
                }
            }
        }
    }
}