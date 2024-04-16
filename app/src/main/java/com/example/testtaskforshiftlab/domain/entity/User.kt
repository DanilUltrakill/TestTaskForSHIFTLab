package com.example.testtaskforshiftlab.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val surName: String,
    val birthDate: BirthDate,
    val password: String
): Parcelable
