package com.example.tam.repository.model

import com.example.tam.repository.HarryPotterService
import retrofit2.Response;

class CharacterRepository {

    suspend fun getHarryPotterResponse(): Response<List<Character>> =
        HarryPotterService.harryPotterService.getHarryPotterResponse()

}