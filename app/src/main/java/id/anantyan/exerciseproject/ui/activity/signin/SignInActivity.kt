package id.anantyan.exerciseproject.ui.activity.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import id.anantyan.exerciseproject.R
import id.anantyan.exerciseproject.database.RoomDB
import id.anantyan.exerciseproject.databinding.ActivitySignInBinding
import id.anantyan.exerciseproject.databinding.DialogExampleBinding
import id.anantyan.exerciseproject.model.Users
import id.anantyan.exerciseproject.repository.UsersRepository
import id.anantyan.exerciseproject.ui.activity.BaseFragmentActivity
import id.anantyan.exerciseproject.ui.fragment.DialogExampleFragment
import id.anantyan.exerciseproject.ui.fragment.DialogExampleFragment.Companion.ARG_DIALOG_EXAMPLE
import id.anantyan.exerciseproject.ui.activity.signup.SignUpActivity
import id.anantyan.utils.Constant.PASSING_TO_SIGN_UP_ACTIVITY
import id.anantyan.utils.Resource
import id.anantyan.utils.Validation.emailValid
import id.anantyan.utils.Validation.passwordValid
import id.anantyan.utils.sharedPreferences.PreferenceHelper
import id.anantyan.utils.sharedPreferences.PreferenceManager
import id.anantyan.utils.sharedPreferences.preference
import id.anantyan.utils.validator.Validator
import id.anantyan.utils.validator.constant.Mode
import id.anantyan.utils.validator.validator

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels {
        val usersDao = RoomDB.database(application).usersDao()
        SignInViewModelFactory(UsersRepository(usersDao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBinding()
        onObserver()
    }

    private fun onBinding() {
        binding.txtLayoutSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.putExtra(PASSING_TO_SIGN_UP_ACTIVITY, true)
            startActivity(intent)
        }
        binding.btnSignIn.setOnClickListener {
            onValidation(this, onSignIn)
        }
        binding.textView5.setOnClickListener {
            onToast(this, "Forgot password!")
        }
    }

    private fun onValidation(context: Context, obj: Validator.OnValidateListener) {
        validator(context) {
            mode = Mode.CONTINUOUS
            listener = obj
            validate(
                emailValid(binding.txtLayoutEmail),
                passwordValid(binding.txtLayoutPassword)
            )
        }
    }

    private val onSignIn = object : Validator.OnValidateListener {
        override fun onValidateSuccess(values: List<String>) {
            onSelectData()
        }

        override fun onValidateFailed(errors: List<String>) {}
    }

    /**
     * Observer LiveData
     * */
    private fun onSelectData() {
        val users = Users(
            id = System.currentTimeMillis().toString(),
            login = binding.txtInputLayoutEmail.text.toString(),
            password = binding.txtInputLayoutPassword.text.toString()
        )
        /*viewModel.selectByUsersLocal(users)*/
        viewModel.signInApi(users)
    }

    private fun onObserver() {
        /*viewModel.selectByUsersLocal.observe(this) {
            if (it != null) {
                application.preference().setLogIn(true)
                val intent = Intent(this@SignInActivity, BaseFragmentActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                onSnackbar(binding.root, getString(R.string.txt_not_found_data))
            }
        }*/
        viewModel.signInResponse.observe(this) {
            when(it) {
                is Resource.Success -> {
                    application.preference().setLogIn(true)
                    application.preference().setUserToken(it.data?.userToken!!)
                    val intent = Intent(this@SignInActivity, BaseFragmentActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is Resource.Loading -> {
                    Toast.makeText(this, "Tunggu...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    it.message?.let { it1 ->
                        onSnackbar(binding.root, it1)
                    }
                }
            }
        }
    }

    /**
     * Materi terkait toast & dialog*/
    private fun onToast(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun onSnackbar(viewContext: View, message: String) {
        Snackbar.make(viewContext, message, Snackbar.LENGTH_LONG).show()
    }

    /**
     * Materi terkait dialog*/
    private fun onDialog(context: Context, title: String, message: String, listener: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setCancelable(false)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Yes") { _, _ ->
            listener.invoke()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun onDialogCustom(context: Context, title: String, message: String, listener: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)
        val binding = DialogExampleBinding.inflate(LayoutInflater.from(context))
        builder.setTitle(title)
        builder.setView(binding.root)
        builder.setCancelable(false)
        val dialog = builder.show()
        binding.txtView.text = message
        binding.btnPositif.setOnClickListener {
            listener.invoke()
        }
        binding.btnNegatif.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun onDialogFragment(title: String, message: String, listener: () -> Unit) {
        val dialog = DialogExampleFragment()
        dialog.show(supportFragmentManager, ARG_DIALOG_EXAMPLE)
    }
}

