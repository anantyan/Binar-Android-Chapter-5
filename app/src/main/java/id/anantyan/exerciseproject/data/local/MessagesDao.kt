package id.anantyan.exerciseproject.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import id.anantyan.exerciseproject.model.Messages

@Dao
interface MessagesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Messages)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: Messages)

    @Delete
    suspend fun delete(item: Messages)

    @Query("DELETE FROM table_messages")
    suspend fun deleteAll()

    @Query("SELECT * FROM table_messages WHERE id=:id")
    suspend fun selectById(id: String): Messages

    @Query("SELECT * FROM table_messages ORDER BY id ASC")
    fun select(): LiveData<List<Messages>>
}