package com.example.testtaskforshiftlab.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BirthDate(
    val day: Int,
    val month: Int,
    val year: Int
): Parcelable {

    override fun toString(): String {
        return "$day/$month/$year"
    }
}
