package com.example.testtaskforshiftlab.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.testtaskforshiftlab.R
import com.example.testtaskforshiftlab.databinding.FragmentContentBinding
import com.example.testtaskforshiftlab.domain.entity.User

class ContentFragment : Fragment() {

    private var _binding: FragmentContentBinding? = null
    private val binding: FragmentContentBinding
        get() = _binding ?: throw RuntimeException("FragmentContentBinding = null")

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
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

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            })

        binding.btnHello.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
                .setTitle(R.string.modal_window)
                .setMessage(String.format(getString(R.string.hello_user), user.name))
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

    private fun parseArgs() {
        requireArguments().getParcelable<User>(KEY_USER)?.let {
            user = it
        }
    }

    companion object {
        private const val KEY_USER = "user"

        fun newInstance(user: User): ContentFragment {
            return ContentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_USER, user)
                }
            }
        }
    }
}