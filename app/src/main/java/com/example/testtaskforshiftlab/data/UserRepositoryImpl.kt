package com.example.testtaskforshiftlab.data

import android.content.SharedPreferences
import com.example.testtaskforshiftlab.domain.entity.BirthDate
import com.example.testtaskforshiftlab.domain.entity.User
import com.example.testtaskforshiftlab.domain.repository.UserRepository

class UserRepositoryImpl(private val sharedPreferences: SharedPreferences) : UserRepository {
    override fun addUser(user: User) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, user.name)
        editor.putString(KEY_SURNAME, user.surName)
        editor.putString(KEY_BIRTH, user.birthDate.toString())
        editor.putString(KEY_PASSWORD, user.password)
        editor.apply()
    }

    override fun getUser(): User? {
        val name = sharedPreferences.getString(KEY_NAME, null)
        val surname = sharedPreferences.getString(KEY_SURNAME, null)
        val birthString = sharedPreferences.getString(KEY_BIRTH, null)
        val password = sharedPreferences.getString(KEY_PASSWORD, null)

        if (name.isNullOrBlank() || surname.isNullOrBlank() || birthString.isNullOrBlank() || password.isNullOrBlank()) {
            return null
        }

        val birth: BirthDate? = try {
            val parts = birthString.split("/")
            if (parts.size == 3) {
                BirthDate(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            } else {
                null
            }
        } catch (e: NumberFormatException) {
            null
        }

        return birth?.let { User(name, surname, it, password) }
    }

    override fun removeUser() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val KEY_NAME = "NAME"
        private const val KEY_SURNAME = "surname"
        private const val KEY_BIRTH = "birth"
        private const val KEY_PASSWORD = "password"
    }

}