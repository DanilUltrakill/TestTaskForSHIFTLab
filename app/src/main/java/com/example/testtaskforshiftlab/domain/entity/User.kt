package com.example.testtaskforshiftlab.domain.entity

data class User(
    val name: String,
    val surName: String,
    val birthDate: BirthDate,
    val password: String
)
