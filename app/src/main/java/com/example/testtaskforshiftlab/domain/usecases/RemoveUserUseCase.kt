package com.example.testtaskforshiftlab.domain.usecases

import com.example.testtaskforshiftlab.domain.entity.User
import com.example.testtaskforshiftlab.domain.repository.UserRepository

class RemoveUserUseCase(private val repository: UserRepository) {

    operator fun invoke() {
        return repository.removeUser()
    }
}