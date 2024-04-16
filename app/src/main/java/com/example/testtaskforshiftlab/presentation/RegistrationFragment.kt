package com.example.testtaskforshiftlab.presentation

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testtaskforshiftlab.R
import com.example.testtaskforshiftlab.databinding.FragmentRegistrationBinding
import com.example.testtaskforshiftlab.domain.entity.BirthDate
import com.example.testtaskforshiftlab.domain.entity.User


class RegistrationFragment: Fragment() {

    private lateinit var viewModel: RegistrationViewModel

    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding
        get() = _binding ?: throw RuntimeException("FragmentRegistrationBinding = null")

    private lateinit var datePickerDialog: DatePickerDialog

    private lateinit var date: BirthDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDatePicker()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        setTodayDate()
        binding.btnDatePicker.text = date.getDateString()
        observeViewModel()
        setOnClickListeners()
        addTextChangeListeners()
    }

    override fun onResume() {
        super.onResume()
        clearInputs()
    }

    private fun clearInputs() {
        with(binding) {
            etName.text = null
            etSurname.text = null
            etPassword.text = null
            etConfirmPassword.text = null
        }
    }

    private fun setOnClickListeners() {
        with(binding) {
            btnDatePicker.setOnClickListener {
                viewModel.resetErrorInputBirth()
                datePickerDialog.show()
            }
            btnRegistration.setOnClickListener {
                if (viewModel.registrationClick(
                    binding.etName.text.toString(),
                    binding.etSurname.text.toString(),
                    date,
                    binding.etPassword.text.toString(),
                    binding.etConfirmPassword.text.toString()
                ))
                    launchContentFragment()
            }
        }
    }

    private fun addTextChangeListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etSurname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputSurname()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputPassword()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputConfirmPassword()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.tilName.error = message
        }
        viewModel.errorInputSurname.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.tilSurname.error = message
        }
        viewModel.errorInputPassword.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_password)
            } else {
                null
            }
            binding.tilPassword.error = message
        }
        viewModel.errorMatchPasswords.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_confirm_password)
            } else {
                null
            }
            binding.tilConfirmPassword.error = message
        }
        viewModel.errorInputBirth.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_birth)
            } else {
                null
            }
            binding.tilBirth.error = message
        }
    }

    private fun launchContentFragment() {
        val newUser = User(
            binding.etName.text.toString(),
            binding.etSurname.text.toString(),
            date,
            binding.etPassword.text.toString(),
            )
        Log.d("RegistrationFragment", newUser.toString())
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ContentFragment.newInstance(newUser))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { _, year, month, day ->
                var curMonth = month
                curMonth += 1
                date = BirthDate(day, curMonth, year)
                binding.btnDatePicker.text = date.getDateString()
            }
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val style = AlertDialog.THEME_HOLO_DARK
        datePickerDialog = DatePickerDialog(requireContext(), style, dateSetListener, year, month, day)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
    }

    private fun setTodayDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        month += 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        date = BirthDate(day, month, year)
    }

    companion object {
    }
}