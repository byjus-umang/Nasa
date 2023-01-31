package com.example.nasa

import android.util.Log
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
       val title : String = "",
       val description: String = "",
       val date: String = "",
       val imageUrl : String = "",
       val showDatePicker : Boolean = false,
       val errorMessage : String = ""

    )
class NasaViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(NasaUiState())
    val uiState: StateFlow<NasaUiState> = _uiState.asStateFlow()

    fun getApodResponse(date: String) {
                viewModelScope.launch(Dispatchers.IO) {
                        try {
                                val response = DataApi.retrofitService.getData(date = date)
                             if (response.isSuccessful) {
                                        val apodResponse = response.body()
                                      _uiState.value = NasaUiState(
                                                title = apodResponse?.title ?: "",
                                                description = apodResponse?.explanation ?: "",
                                                date = apodResponse?.date ?: "",
                                                imageUrl = apodResponse?.url ?: "",
                                              errorMessage = ""
                                                  )
                                    } else {
                                        _uiState.value = _uiState.value.copy(
                                              errorMessage = "Oops! Something went wrong!"
                                                    )
                                    }
                            } catch (exc : Exception) {
                               _uiState.value = _uiState.value.copy(
                                       errorMessage = "Oops! Something went wrong!"
                                          )

                          }
        }
          }

       fun showDatePicker() {
             _uiState.value = _uiState.value.copy(
                      showDatePicker = true,
                      errorMessage = ""
                          )
           }

       fun hideDatePicker() {
                _uiState.value = _uiState.value.copy(
            showDatePicker = false,
                    errorMessage = ""
        )
    }



}