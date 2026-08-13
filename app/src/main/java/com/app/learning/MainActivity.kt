package com.app.learning

import android.os.Bundle
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
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.learning.ui.theme.LearningTheme

class MainActivity : ComponentActivity(){
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
fun StudentScreen(){
    var name by remember{
        mutableStateOf("")
    }

    var course by remember {
        mutableStateOf("")
    }
    var savedName by remember {
        mutableStateOf("")
    }

    var savedCourse by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Student Management"
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Student Name : ")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)

        )
        OutlinedTextField(
            value = course,
            onValueChange = {
                course = it
            },
            label = {
                Text("Course : ")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        Button(
            onClick = {
                savedName = name
                savedCourse = course
                println("Name: $name, Course: $course")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Student")
        }
    }
}
