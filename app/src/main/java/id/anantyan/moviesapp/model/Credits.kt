package id.anantyan.moviesapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.anantyan.utils.Constant.BASE_IMAGE
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credits(

	@SerializedName("cast")
		val cast: List<CastItem>? = null,

	@SerializedName("status_message")
	val statusMessage: String? = null,

	@SerializedName("status_code")
	val statusCode: Int? = null,

	@SerializedName("success")
	val success: Boolean? = null
) : Parcelable

@Parcelize
data class CastItem(

	@SerializedName("cast_id")
	val castId: Int? = null,

	@SerializedName("character")
	val character: String? = null,

	@SerializedName("gender")
	val gender: Int? = null,

	@SerializedName("credit_id")
	val creditId: String? = null,

	@SerializedName("known_for_department")
	val knownForDepartment: String? = null,

	@SerializedName("original_name")
	val originalName: String? = null,

	@SerializedName("popularity")
	val popularity: Double? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("profile_path")
	val _profilePath: String? = null,

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("adult")
	val adult: Boolean? = null,

	@SerializedName("order")
	val order: Int? = null
) : Parcelable {
	val profilePath get() = "${BASE_IMAGE}${_profilePath}"
}
