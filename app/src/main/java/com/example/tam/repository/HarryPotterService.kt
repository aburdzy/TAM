package com.example.tam.repository

import com.example.tam.repository.model.Character
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface HarryPotterService {

    @GET("/api/characters")
    suspend fun getHarryPotterResponse(): Response<List<Character>>

    @GET("/api/character/{id}")
    suspend fun getHarryPotterDetailsResponse(@Path("id") id: String): Response<List<Character>>

    companion object {
        private const val URL = "https://hp-api.onrender.com"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val harryPotterService: HarryPotterService by lazy {
            retrofit.create(HarryPotterService::class.java)
        }
    }
}