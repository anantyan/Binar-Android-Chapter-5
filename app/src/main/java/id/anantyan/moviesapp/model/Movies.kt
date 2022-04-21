package id.anantyan.moviesapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import id.anantyan.utils.Constant.BASE_IMAGE
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movies(

    @SerializedName("results")
    val results: List<ResultsItem>? = null,

    @SerializedName("status_message")
    val statusMessage: String? = null,

    @SerializedName("status_code")
    val statusCode: Int? = null,

    @SerializedName("success")
    val success: Boolean? = null
) : Parcelable

@Parcelize
data class ResultsItem(

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("video")
    val video: Boolean? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("genre_ids")
    val genreIds: List<Int?>? = null,

    @SerializedName("poster_path")
    val _posterPath: String? = null,

    @SerializedName("backdrop_path")
    val _backdropPath: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("media_type")
    val mediaType: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("adult")
    val adult: Boolean? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null
) : Parcelable {
    val backdropPath get() = "${BASE_IMAGE}${_backdropPath}"
    val posterPath get() = "${BASE_IMAGE}${_posterPath}"
}
