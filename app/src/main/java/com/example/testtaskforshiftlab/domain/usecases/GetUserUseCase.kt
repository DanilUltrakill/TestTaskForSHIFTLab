package com.example.testtaskforshiftlab.domain.usecases

import com.example.testtaskforshiftlab.domain.entity.User
import com.example.testtaskforshiftlab.domain.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository) {
    operator fun invoke(): User? {
        return repository.getUser()
    }
}