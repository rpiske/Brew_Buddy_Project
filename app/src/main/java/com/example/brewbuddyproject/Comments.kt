package com.example.brewbuddyproject

import java.io.Serializable

data class Comments(

    val name: String,
    val user: String,
    val comment: String,
    val commentNumber: String
): Serializable
