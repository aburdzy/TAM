package com.example.tam.repository.model

data class Character (
    var id: String,
    var name: String,
    var house: String,
    var actor: String,
    var species: String,
    var image: String,
    var wand: Wand,
    var dateOfBirth: String,
    var ancestry: String
)

data class Wand (
    var wood: String,
    var core: String,
    var length: Number,
)