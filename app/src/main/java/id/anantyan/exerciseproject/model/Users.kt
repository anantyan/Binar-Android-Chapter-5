package id.anantyan.exerciseproject.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "table_users")
@Parcelize
data class Users(
    @SerializedName("objectId")
    @PrimaryKey
    val id: String,
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("email")
    @ColumnInfo(name = "email")
    val email: String? = null,
    @SerializedName("password")
    @ColumnInfo(name = "password")
    val password: String? = null,
    @SerializedName("name")
    @ColumnInfo(name = "fullName")
    val fullName: String? = null,
    @SerializedName("job")
    @ColumnInfo(name = "pekerjaan")
    val pekerjaan: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("user-token")
    val userToken: String? = null
) : Parcelable

@Parcelize
data class UsersResponse(
    @SerializedName("users")
    val items: List<Users>? = null,
    @SerializedName("users")
    val item: Users? = null
) : Parcelable