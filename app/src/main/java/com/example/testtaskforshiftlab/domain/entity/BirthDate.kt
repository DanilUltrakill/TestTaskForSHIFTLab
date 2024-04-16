package com.example.testtaskforshiftlab.domain.entity

import java.time.Month

data class BirthDate(
    val day: Int,
    val month: Int,
    val year: Int
) {
    fun getDateString(): String {
        return "$day/$month/$year"
    }
}
