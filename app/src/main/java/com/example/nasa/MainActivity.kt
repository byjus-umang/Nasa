package com.example.nasa


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage

import kotlinx.coroutines.launch
import java.util.*



class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition",
        "SuspiciousIndentation"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val viewModel: NasaViewModel by viewModels()
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect {


                    }
                }
            }


            val date1:String

            viewModel.update()
            Architecture(viewModel)
            date1=showDatePicker(this)



        }
    }
}
@Composable
fun imagecard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier=Modifier
){


        Box(modifier = modifier.padding(top=50.dp, bottom = 50.dp))
        {
            Image(painter = painter, contentDescription =contentDescription, contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height((400.dp)))
        }


}
@Composable
fun showDatePicker(context: MainActivity):String{

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    var date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/${month+1}/$year"
        }, year, month, day
    )

    Column(

        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {



        Button(shape= CircleShape,
            colors= ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFC3D21)),
            onClick = {
                datePickerDialog.show()
            }) {
            Text(text = " Date ")
        }
    }
return date.value
}
@Composable
fun Architecture(viewModel:NasaViewModel)
{    val painter= painterResource(id = R.drawable.pexels_1)
    val contentDescription="Meteoroids"
    val title="Passing by"
    Column() {
       Column(Modifier.padding(5.dp)) {
           Text(text = viewModel.data.title, fontSize = 20.sp, style= TextStyle(textDecoration = TextDecoration.Underline), fontWeight = FontWeight.Bold)
       }

        Spacer(modifier = Modifier.height(20.dp))
//        imagecard(painter = painter, contentDescription =contentDescription , title =title,
//            Modifier
//
//                .background(color = Color.Gray))
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
            AsyncImage(model = viewModel.data.url, contentDescription =null, modifier = Modifier.size(500.dp), contentScale = ContentScale.Crop, alignment =Alignment.Center)
        }


        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text=viewModel.data.date, fontSize = 20.sp,fontWeight = FontWeight.Bold)
        }

        Text(textAlign = TextAlign.Center,
            text=viewModel.data.explanation, overflow =TextOverflow.Ellipsis,fontWeight = FontWeight.Bold
        )
    }
}




