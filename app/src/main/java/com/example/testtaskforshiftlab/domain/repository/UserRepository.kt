package com.example.testtaskforshiftlab.domain.repository

import com.example.testtaskforshiftlab.domain.entity.User

interface UserRepository {

    fun addUser(user: User)

    fun getUser(): User?

    fun removeUser()
}