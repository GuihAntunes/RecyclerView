package com.example.logonrmlocal.recyclerview.model

data class PokemonResponse(val content: List<Pokemon>)

data class Pokemon(
        val number: Int,
        val name: String,
        val imageURL: String
)