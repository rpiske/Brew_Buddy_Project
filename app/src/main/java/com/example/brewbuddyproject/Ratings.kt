package com.example.brewbuddyproject

import java.io.Serializable

data class Ratings(

    val name: String,
    val user: String,
    val rating: Float
): Serializable
