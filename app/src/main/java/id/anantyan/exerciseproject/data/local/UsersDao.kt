package id.anantyan.exerciseproject.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.anantyan.exerciseproject.model.Users

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Users)

    @Query("SELECT * FROM table_users WHERE email=:email AND password=:password")
    suspend fun selectByUsers(email: String?, password: String?): Users
}