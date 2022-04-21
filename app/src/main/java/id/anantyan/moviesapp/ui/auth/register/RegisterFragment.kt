package id.anantyan.moviesapp.ui.auth.register

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.database.RoomDB
import id.anantyan.moviesapp.databinding.FragmentRegisterBinding
import id.anantyan.moviesapp.model.Users
import id.anantyan.moviesapp.repository.UsersRepository
import id.anantyan.utils.Resource
import id.anantyan.utils.confirmPasswordValid
import id.anantyan.utils.emailValid
import id.anantyan.utils.passwordValid
import id.anantyan.utils.validator.Validator
import id.anantyan.utils.validator.constant.Mode
import id.anantyan.utils.validator.validator

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels {
        val usersDao = RoomDB.database(requireContext()).usersDao()
        RegisterViewModelFactory(UsersRepository(usersDao))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
        onBindObserver()
    }

    private fun onBindView() {
        binding.btnSignUp.setOnClickListener {
            onValidation()
        }
    }

    private fun onBindObserver() {
        viewModel.register.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    onToast("${it.message}")
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
                else -> {}
            }
        }
    }

    private fun onValidation() {
        validator(requireContext()) {
            mode = Mode.SINGLE
            listener = onSignUp
            validate(
                emailValid(binding.txtLayoutEmail),
                passwordValid(binding.txtLayoutPassword),
                confirmPasswordValid(binding.txtLayoutConfirmPassword, binding.txtInputPassword)
            )
        }
    }

    private fun onSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
        snackbar.show()
    }

    private fun onToast(message: String) {
        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val onSignUp = object : Validator.OnValidateListener {
        override fun onValidateSuccess(values: List<String>) {
            val item = Users(
                email = binding.txtInputEmail.text.toString(),
                password = binding.txtInputPassword.text.toString()
            )
            viewModel.register(item)
        }

        override fun onValidateFailed(errors: List<String>) {}
    }
}