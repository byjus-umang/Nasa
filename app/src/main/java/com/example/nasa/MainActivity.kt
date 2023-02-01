package com.example.nasa


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import java.util.Calendar
import java.util.Date
import kotlinx.coroutines.launch
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                val viewModel: NasaViewModel by viewModels()

               lifecycleScope.launch {
                     repeatOnLifecycle(Lifecycle.State.STARTED) {
                                viewModel.getApodResponse("")
                           }
                   }
        setContent {
                     NasaAPODScreen(viewModel)
        }
    }
    }

@SuppressLint("SuspiciousIndentation")
@Composable
fun NasaAPODScreen(
        viewModel:NasaViewModel,
       modifier: Modifier = Modifier
            ) {
        val uiState = viewModel.uiState.collectAsState()
      val context = LocalContext.current
       Column(
              modifier = modifier
                  .verticalScroll(rememberScrollState())
                  .fillMaxWidth()
                  .fillMaxHeight()
                  .padding(8.dp)

                    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .align(Alignment.CenterStart),
                        text = uiState.value.title,
                        fontSize = 20.sp,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        fontWeight = FontWeight.Bold,
                    )

                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFC3D21)),
                        onClick = {
                            viewModel.showDatePicker()
                        }
                    ) {
                        Text(text = "Date")
                    }
                }
            }

                Spacer(modifier = Modifier.height(20.dp))
                
                       Box(
                              Modifier
                                  .fillMaxWidth()
                                  .align(Alignment.CenterHorizontally)

                                    ) {

                               AsyncImage(
                                        modifier = Modifier.size(400.dp),
                                        model = uiState.value.imageUrl,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        alignment =Alignment.Center)
                            }

                       Text(
                              modifier = Modifier
                                  .align(Alignment.CenterHorizontally)
                                  .padding(10.dp),

                                   text = uiState.value.date,
                                   fontSize = 20.sp,
                                   fontWeight = FontWeight.Bold
                )
                Text(
                                textAlign = TextAlign.Center,
                                text = uiState.value.description,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold
                                   )
                   }

               if (uiState.value.showDatePicker) {
                        ShowDatePicker(context = context)
                   }

              if (uiState.value.errorMessage.isNotEmpty()) {
                        Toast.makeText(context, uiState.value.errorMessage, Toast.LENGTH_SHORT).show()
                   }
        }


@Composable
 fun ShowDatePicker(context: Context) {

       val viewModel : NasaViewModel = viewModel()
    val year: Int
    val month: Int
    val day: Int
    var calendar=Calendar.getInstance()
    year=calendar.get(Calendar.YEAR)
    month=calendar.get(Calendar.MONTH)
     day = calendar.get(Calendar.DAY_OF_MONTH)
     calendar.time = Date()
        val date = remember { mutableStateOf("") }
     val datePickerDialog = DatePickerDialog(
         context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                         date.value = "$year-${month+1}-$dayOfMonth"
                         viewModel.getApodResponse(date.value)
                         viewModel.hideDatePicker()
         }, year, month, day
     )
    datePickerDialog.show()
}





