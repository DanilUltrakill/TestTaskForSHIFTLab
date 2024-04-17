package com.example.testtaskforshiftlab.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtaskforshiftlab.data.UserRepositoryImpl
import com.example.testtaskforshiftlab.domain.entity.User
import com.example.testtaskforshiftlab.domain.usecases.GetUserUseCase
import com.example.testtaskforshiftlab.domain.usecases.RemoveUserUseCase

class ContentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepositoryImpl(
        application.getSharedPreferences(
            RegistrationViewModel.KEY_SHARED_PREF,
            Context.MODE_PRIVATE
        )
    )

    private val removeUserUseCase = RemoveUserUseCase(repository)
    private val getUserUseCase = GetUserUseCase(repository)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun getUser() {
        getUserUseCase()?.let {
            _user.value = it
        }
    }

    fun removeUser() {
        removeUserUseCase()
    }
}