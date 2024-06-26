package com.example.testtaskforshiftlab.presentation

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtaskforshiftlab.data.UserRepositoryImpl
import com.example.testtaskforshiftlab.domain.entity.BirthDate
import com.example.testtaskforshiftlab.domain.entity.User
import com.example.testtaskforshiftlab.domain.usecases.AddUserUseCase
import com.example.testtaskforshiftlab.domain.usecases.GetUserUseCase

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserRepositoryImpl(
        application.getSharedPreferences(
            KEY_SHARED_PREF,
            MODE_PRIVATE
        )
    )

    private val addUserUseCase = AddUserUseCase(repository)
    private val getUserUseCase = GetUserUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputSurname = MutableLiveData<Boolean>()
    val errorInputSurname: LiveData<Boolean>
        get() = _errorInputSurname

    private val _errorInputBirth = MutableLiveData<Boolean>()
    val errorInputBirth: LiveData<Boolean>
        get() = _errorInputBirth

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private val _errorMatchPasswords = MutableLiveData<Boolean>()
    val errorMatchPasswords: LiveData<Boolean>
        get() = _errorMatchPasswords


    private val _regIsValid = MutableLiveData<Boolean>()
    val regIsValid: LiveData<Boolean>
        get() = _regIsValid

    fun checkUser() {
        getUserUseCase()?.let {
            _regIsValid.value = true
        }
    }

    fun registrationClick(
        inputName: String?,
        inputSurname: String?,
        inputBirth: BirthDate,
        inputPassword: String?,
        inputConfirmPassword: String?
    ) {
        val name = parseInput(inputName)
        val surname = parseInput(inputSurname)
        val password = parseInput(inputPassword)
        val confirmPassword = parseInput(inputConfirmPassword)

        if (validateInput(name, surname, inputBirth, password, confirmPassword)) {
            val newUser = User(name, surname, inputBirth, password)
            addUserUseCase(newUser)
            _regIsValid.value = true
        } else
            _regIsValid.value = false
    }

    private fun parseInput(input: String?): String {
        return input?.trim() ?: ""
    }

    private fun validateInput(
        name: String,
        surname: String,
        birth: BirthDate,
        password: String,
        confirmPassword: String
    ): Boolean {
        var result = true
        if (!validateName(name)) {
            _errorInputName.value = true
            result = false
        }
        if (!validateName(surname)) {
            _errorInputSurname.value = true
            result = false
        }
        if (!validateBirth(birth)) {
            _errorInputBirth.value = true
            result = false
        }
        if (!validatePassword(password)) {
            _errorInputPassword.value = true
            result = false
        }
        if (!passwordsIsMatch(password, confirmPassword)) {
            _errorMatchPasswords.value = true
            result = false
        }
        return result
    }

    private fun validateBirth(birth: BirthDate): Boolean {
        return birth.year < MAX_INPUT_YEAR
    }

    private fun validateName(name: String): Boolean {
        if (name.isBlank())
            return false
        val regexRu = REGEX_RU.toRegex()
        val regex = REGEX_EN.toRegex()
        if (!(name.matches(regexRu) || name.matches(regex)))
            return false
        if (name.length > MAX_INPUT_NAME)
            return false
        return true
    }

    private fun validatePassword(password: String): Boolean {
        if (password.isBlank())
            return false
        if (password.length < MIN_INPUT_PASSWORD)
            return false
        return true
    }

    private fun passwordsIsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputSurname() {
        _errorInputSurname.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

    fun resetErrorInputConfirmPassword() {
        _errorMatchPasswords.value = false
    }

    fun resetErrorInputBirth() {
        _errorInputBirth.value = false
    }

    companion object {
        private const val REGEX_RU = "[а-яА-ЯёЁ]+"
        private const val REGEX_EN = "[a-zA-Z]+"
        private const val MAX_INPUT_NAME = 15
        private const val MIN_INPUT_PASSWORD = 5
        private const val MAX_INPUT_YEAR = 2010
        const val KEY_SHARED_PREF = "myPrefs"
    }

}