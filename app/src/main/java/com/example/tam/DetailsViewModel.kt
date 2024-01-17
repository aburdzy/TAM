package com.example.tam

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tam.repository.model.Character
import com.example.tam.repository.model.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel: ViewModel() {
    private val characterRepository = CharacterRepository()

    private val mutableCharacterDetails = MutableLiveData<UiState<List<Character>>>()
    val immutableCharacterDetails: LiveData<UiState<List<Character>>> = mutableCharacterDetails

    fun getData(id: String) {
        mutableCharacterDetails.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = characterRepository.getHarryPotterDetailsResponse(id)
                Log.e("DetailsViewModel", "request return code: ${request.code()}")

                if (request.isSuccessful) {
                    val characterDetails = request.body()
                    mutableCharacterDetails.postValue(UiState(data = characterDetails!!))
                } else {
                    mutableCharacterDetails.postValue(UiState(error = "${request.code()}"))
                    Log.e("DetailsViewModel", "Request failed, ${request.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Request failed", e)
            }
        }
    }
}