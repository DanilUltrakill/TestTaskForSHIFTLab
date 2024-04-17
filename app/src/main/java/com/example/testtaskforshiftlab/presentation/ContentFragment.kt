package com.example.testtaskforshiftlab.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testtaskforshiftlab.R
import com.example.testtaskforshiftlab.databinding.FragmentContentBinding
import com.example.testtaskforshiftlab.domain.entity.User

class ContentFragment : Fragment() {

    private var _binding: FragmentContentBinding? = null
    private val binding: FragmentContentBinding
        get() = _binding ?: throw RuntimeException("FragmentContentBinding = null")

    private lateinit var viewModel: ContentViewModel

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ContentViewModel::class.java]
        viewModel.getUser()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val builder = AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.unlogin))
                        .setMessage(getString(R.string.unlogin_desc))
                        .setPositiveButton(getString(R.string.exit_modal_window)) { _, _ ->
                            viewModel.removeUser()
                            requireActivity().supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.main_container, RegistrationFragment())
                                .commit()
                        }
                        .setNegativeButton(getString(R.string.cancel_modal_window)) { dialog, _ ->
                            dialog.cancel()
                        }

                    val dialog = builder.create()
                    dialog.show()
                }
            })

        viewModel.user.observe(viewLifecycleOwner) {
            user = it
        }

        binding.btnHello.setOnClickListener {

            val nameString = if (user.name.isBlank())
                getString(R.string.error_get_user)
            else user.name

            val builder = AlertDialog.Builder(requireContext())
                .setTitle(R.string.modal_window)
                .setMessage(String.format(getString(R.string.hello_user), nameString))
                .setPositiveButton(getString(R.string.close_modal_window)) { dialog, _ ->
                    dialog.cancel()
                }

            val dialog = builder.create()
            dialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}