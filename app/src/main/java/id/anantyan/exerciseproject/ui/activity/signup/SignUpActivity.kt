package id.anantyan.exerciseproject.ui.activity.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import id.anantyan.exerciseproject.R
import id.anantyan.exerciseproject.database.RoomDB
import id.anantyan.exerciseproject.databinding.ActivitySignUpBinding
import id.anantyan.exerciseproject.model.Users
import id.anantyan.exerciseproject.repository.UsersRepository
import id.anantyan.exerciseproject.ui.UsersViewModelFactory
import id.anantyan.exerciseproject.ui.activity.signin.SignInActivity
import id.anantyan.utils.Constant.PASSING_TO_SIGN_UP_ACTIVITY
import id.anantyan.utils.Resource
import id.anantyan.utils.Validation.emailValid
import id.anantyan.utils.Validation.namelValid
import id.anantyan.utils.Validation.passwordValid
import id.anantyan.utils.validator.Validator
import id.anantyan.utils.validator.constant.Mode
import id.anantyan.utils.validator.validator


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var users: Users
    private val viewModel: SignUpViewModel by viewModels {
        val usersDao = RoomDB.database(application).usersDao()
        UsersViewModelFactory(UsersRepository(usersDao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.setTitle(R.string.page_sign_up)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        onBinding()
        onObserver()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (onCheckIntent()) {
            super.onBackPressed()
        } else {
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
            startActivity(intent)
        }
    }

    private fun onBinding() {
        binding.btnContinue.setOnClickListener {
            onValidation()
        }
    }

    private fun onCheckIntent(): Boolean {
        return intent.hasExtra(PASSING_TO_SIGN_UP_ACTIVITY)
    }

    private fun onValidation() {
        validator(this) {
            mode = Mode.CONTINUOUS
            listener = onSignUp
            validate(
                namelValid(binding.txtLayoutFullName),
                emailValid(binding.txtLayoutEmail),
                passwordValid(binding.txtLayoutPassword)
            )
        }
    }

    /**
     * Observer LiveData
     * */
    /*private fun onSelectData() {
        viewModel.selectByUsersLocal(users)
    }*/

    private fun onInsertData() {
        val users = Users(
            id = System.currentTimeMillis().toString(),
            fullName = binding.txtInputLayoutFullName.text.toString(),
            pekerjaan = binding.txtInputLayoutPekerjaan.text.toString(),
            email = binding.txtInputLayoutEmail.text.toString(),
            password = binding.txtInputLayoutPassword.text.toString()
        )
        /*viewModel.insertLocal(users)*/
        viewModel.signUpApi(users)
    }

    private fun onObserver() {
        /*viewModel.insertLocal.observe(this) {
            onToast(this, "Data berhasil dibuat!")
            onBackPressed()
        }
        viewModel.selectByUsersLocal.observe(this) {
            if (it == null) {
                onInsertData()
            } else {
                onSnackbar(binding.root, getString(R.string.txt_found_data))
            }
        }*/
        viewModel.signUpResponse.observe(this) {
            when(it) {
                is Resource.Success -> {
                    onToast(this, it.message!!)
                    onBackPressed()
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

    private fun onToast(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun onSnackbar(viewContext: View, message: String) {
        Snackbar.make(viewContext, message, Snackbar.LENGTH_LONG).show()
    }

    private val onSignUp = object : Validator.OnValidateListener {
        override fun onValidateSuccess(values: List<String>) {
            /*onSelectData()*/
            onInsertData()
        }

        override fun onValidateFailed(errors: List<String>) {}
    }
}