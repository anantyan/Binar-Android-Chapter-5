package id.anantyan.moviesapp.ui.auth.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.database.RoomDB
import id.anantyan.moviesapp.databinding.FragmentLoginBinding
import id.anantyan.moviesapp.model.Users
import id.anantyan.moviesapp.repository.UsersRepository
import id.anantyan.utils.Resource
import id.anantyan.utils.emailValid
import id.anantyan.utils.passwordValid
import id.anantyan.utils.sharedPreferences.preference
import id.anantyan.utils.validator.Validator
import id.anantyan.utils.validator.constant.Mode
import id.anantyan.utils.validator.validator

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels {
        val usersDao = RoomDB.database(requireContext()).usersDao()
        LoginViewModelFactory(UsersRepository(usersDao))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
        onBindObserver(view)
    }

    private fun onBindView() {
        binding.btnSignIn.setOnClickListener {
            onValidation()
        }
        binding.txtSignUp.setOnClickListener {
            val destination = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            it.findNavController().navigate(destination)
        }
    }

    private fun onBindObserver(view: View) {
        viewModel.login.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    requireContext().preference().setLogIn(true)
                    requireContext().preference().setUserId(it.data?.userId!!)
                    val destination = LoginFragmentDirections.actionLoginFragmentToMainActivity()
                    view.findNavController().navigate(destination)
                    requireActivity().finish()
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
            listener = onSignIn
            validate(
                emailValid(binding.txtLayoutEmail),
                passwordValid(binding.txtLayoutPassword)
            )
        }
    }

    private fun onSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val onSignIn = object : Validator.OnValidateListener {
        override fun onValidateSuccess(values: List<String>) {
            val item = Users(
                email = binding.txtInputEmail.text.toString(),
                password = binding.txtInputPassword.text.toString()
            )
            viewModel.login(item)
        }

        override fun onValidateFailed(errors: List<String>) {}
    }
}