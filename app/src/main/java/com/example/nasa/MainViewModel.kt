package com.example.nasa

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NasaUiState(
    var description: String,
    var title : String,
    var date: String,

    )
class NasaViewModel: ViewModel() {
    private var _uiState = MutableStateFlow(NasaUiState(description = "Hey", title = "Hii", date = "Hey"))
    val uiState: StateFlow<NasaUiState> = _uiState.asStateFlow()
      var data:UserResponse by mutableStateOf(UserResponse(date = "ss",
          explanation = "ssss", hdurl = "sss", media_type = "Fe", service_version = "sss", title = "xsw", url = "xsss", copyright = "xsxs"))
   fun update(){
     this._uiState.value.title="Wow"
       _uiState.value.description="Meteoroids are objects in space that range in size from dust grains to small asteroids. Think of them as “space rocks.\" When meteoroids enter Earth's atmosphere (or that of another planet, like Mars) at high speed and burn up, the fireballs or “shooting stars” are called meteors."
       _uiState.value.date="Hiiiiii"



   }
    init{
        viewModelScope.launch(Dispatchers.IO){
            val response= dataApi.retrofitService.getdata().body() ?: UserResponse()
            data=response
        }


    }

}