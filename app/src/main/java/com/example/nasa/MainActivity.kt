package com.example.nasa

import android.app.DatePickerDialog
import android.content.ClipDescription
import android.content.Context
import android.graphics.Color.rgb


import android.os.Bundle
import android.widget.DatePicker
import android.widget.TableRow
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nasa.ui.theme.NASATheme
import java.util.*
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val painter= painterResource(id = R.drawable.pexels_1)
            val contentDescription="Meteoroids"
            val title="Passing by"
            showDatePicker(this)
                    Column() {
                Text(text = "The lobster Nebula", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(20.dp))
                imagecard(painter = painter, contentDescription =contentDescription , title =title,
                    Modifier

                        .background(color = Color.Gray))
                Text(textAlign = TextAlign.Center,
                    text="Galaxies are sprawling systems of dust, gas, dark matter, and anywhere from a million to a trillion stars that are held together by gravity. Nearly all large galaxies are thought to also contain supermassive black holes at their centers. In our own galaxy, the Milky Way, the sun is just one of about 100 to 400 billion stars that spin around Sagittarius A*, a supermassive black hole that contains as much mass as four million suns.", overflow =TextOverflow.Ellipsis, maxLines = 2
                )

            }
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
            modifier = Modifier.fillMaxWidth().height((400.dp)))
        }


}
@Composable
fun showDatePicker(context: Context){

    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            date.value = "$dayOfMonth/$month/$year"
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

}

