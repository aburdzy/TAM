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

class MainViewModel:  ViewModel() {
    private val characterRepository = CharacterRepository()

    private val mutableCharacterData = MutableLiveData<UiState<List<Character>>>()
    val immutableCharacterData: LiveData<UiState<List<Character>>> = mutableCharacterData

    fun getData() {
        mutableCharacterData.postValue(UiState(isLoading = true))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = characterRepository.getHarryPotterResponse()
                Log.e("MainViewModel", "request return code: ${request.code()}")

                if(request.isSuccessful) {
                    val harryPotters = request.body()
                    mutableCharacterData.postValue(UiState(data=harryPotters!!))
                }
                else {
                    mutableCharacterData.postValue(UiState(error="${request.code()}"))
                    Log.e("MainViewModel", "Request failed, ${request.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Request failed", e)
            }
        }
    }
}