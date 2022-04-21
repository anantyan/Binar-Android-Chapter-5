package id.anantyan.moviesapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val resId: Int? = null,
    val title: String? = null,
    val field: String? = null
) : Parcelable
