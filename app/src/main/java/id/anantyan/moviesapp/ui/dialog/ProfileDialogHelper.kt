package id.anantyan.moviesapp.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import id.anantyan.moviesapp.model.Profile
import id.anantyan.moviesapp.model.Users

interface ProfileDialogHelper {
    fun dialogSetPassword(userId: Int?, listener: (item: Users, dialog: AlertDialog) -> Unit)
    fun dialogSetProfile(users: Users, listener: (item: Users, dialog: AlertDialog) -> Unit)
    fun dialogLogout(listener: (dialog: AlertDialog) -> Unit)
}

fun Context.dialog(): ProfileDialogHelper {
    val dialog: ProfileDialogHelper by lazy { ProfileDialog(this) }
    return dialog
}