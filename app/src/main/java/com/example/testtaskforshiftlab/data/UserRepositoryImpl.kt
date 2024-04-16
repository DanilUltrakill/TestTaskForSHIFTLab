package com.example.testtaskforshiftlab.data

import android.content.SharedPreferences
import com.example.testtaskforshiftlab.domain.entity.User
import com.example.testtaskforshiftlab.domain.repository.UserRepository

class UserRepositoryImpl(private val sharedPreferences: SharedPreferences): UserRepository {
    override fun addUser(user: User) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, user.name)
        editor.putString(KEY_SURNAME, user.surName)
        editor.putString(KEY_BIRTH, user.birthDate.toString())
        editor.putString(KEY_PASSWORD, user.password)
        editor.apply()
    }

    override fun getUser(): User {
        TODO("Not yet implemented")
    }

    override fun removeUser() {
        TODO("Not yet implemented")
    }

    companion object {
        private const val KEY_NAME = "NAME"
        private const val KEY_SURNAME = "surname"
        private const val KEY_BIRTH = "birth"
        private const val KEY_PASSWORD = "password"
    }

}