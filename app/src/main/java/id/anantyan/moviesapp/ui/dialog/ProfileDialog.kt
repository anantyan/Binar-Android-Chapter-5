package id.anantyan.moviesapp.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.anantyan.moviesapp.databinding.DialogSetProfileFirstBinding
import id.anantyan.moviesapp.databinding.DialogSetProfileSecondBinding
import id.anantyan.moviesapp.model.Profile
import id.anantyan.moviesapp.model.Users
import id.anantyan.utils.*
import id.anantyan.utils.validator.Validator
import id.anantyan.utils.validator.constant.Mode
import id.anantyan.utils.validator.validator

class ProfileDialog(private val context: Context) : ProfileDialogHelper {
    override fun dialogSetPassword(userId: Int?, listener: (item: Users, dialog: AlertDialog) -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)
        val binding = DialogSetProfileSecondBinding.inflate(LayoutInflater.from(context))
        builder.setCancelable(false)
        builder.setTitle("Ganti Password")
        builder.setView(binding.root)
        builder.setPositiveButton("Simpan", null)
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.show()
        val btnPositif = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        btnPositif.setOnClickListener {
            val item = Users(
                userId = userId,
                password = binding.txtInputConfirmPassword.text.toString().trim(),
            )
            validator(context) {
                this.mode = Mode.SINGLE
                this.listener = onSetDialog(item, dialog, listener)
                this.validate(
                    passwordValid(binding.txtLayoutPassword),
                    confirmPasswordValid(binding.txtLayoutConfirmPassword, binding.txtInputPassword)
                )
            }
        }
    }

    override fun dialogSetProfile(users: Users, listener: (item: Users, dialog: AlertDialog) -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)
        val binding = DialogSetProfileFirstBinding.inflate(LayoutInflater.from(context))
        binding.txtInputFullname.setText(users.fullname)
        binding.txtInputUsername.setText(users.username)
        binding.txtInputEmail.setText(users.email)
        builder.setCancelable(false)
        builder.setTitle("Ubah Data Akun")
        builder.setView(binding.root)
        builder.setPositiveButton("Simpan", null)
        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.show()
        val btnPositif = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        btnPositif.setOnClickListener {
            val item = Users(
                userId = users.userId,
                fullname = binding.txtInputFullname.text.toString().trim(),
                username = binding.txtInputUsername.text.toString().trim(),
                email = binding.txtInputEmail.text.toString().trim()
            )
            validator(context) {
                this.mode = Mode.SINGLE
                this.listener = onSetDialog(item, dialog, listener)
                this.validate(
                    generalValid(binding.txtLayoutFullname),
                    usernameValid(binding.txtLayoutUsername),
                    emailValid(binding.txtLayoutEmail)
                )
            }
        }
    }

    override fun dialogLogout(listener: (dialog: AlertDialog) -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)
        builder.setCancelable(false)
        builder.setTitle("Attention!")
        builder.setMessage("Apakah anda ingin keluar dari akun ini?")
        builder.setPositiveButton("Iya", null)
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.show()
        val btnPositif = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        btnPositif.setOnClickListener {
            listener.invoke(dialog)
        }
    }

    private fun onSetDialog(
        item: Users,
        dialog: AlertDialog,
        listener: (item: Users, dialog: AlertDialog) -> Unit
    ) = object : Validator.OnValidateListener {
        override fun onValidateSuccess(values: List<String>) {
            listener.invoke(item, dialog)
        }

        override fun onValidateFailed(errors: List<String>) {}
    }
}