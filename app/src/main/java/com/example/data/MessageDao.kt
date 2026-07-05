package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getAllMessagesFlow(): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE category = :category ORDER BY id ASC")
    fun getMessagesByCategoryFlow(category: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE category = :category AND subcategory = :subcategory ORDER BY id ASC")
    fun getMessagesByCategoryAndSubcategoryFlow(category: String, subcategory: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteMessagesFlow(): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE collectionName = :collectionName ORDER BY timestamp DESC")
    fun getMessagesInCollectionFlow(collectionName: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE id = :id")
    suspend fun getMessageById(id: Int): MessageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>)

    @Update
    suspend fun updateMessage(message: MessageEntity)

    @Query("DELETE FROM messages WHERE id = :id")
    suspend fun deleteMessageById(id: Int)

    @Query("DELETE FROM messages WHERE isCustom = 0")
    suspend fun deleteAllDefaultMessages()

    @Query("SELECT COUNT(*) FROM messages")
    suspend fun getMessagesCount(): Int

    // Custom bookmark collections
    @Query("SELECT * FROM bookmark_collections ORDER BY name ASC")
    fun getAllCollectionsFlow(): Flow<List<BookmarkCollectionEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCollection(collection: BookmarkCollectionEntity)

    @Query("DELETE FROM bookmark_collections WHERE name = :name")
    suspend fun deleteCollectionByName(name: String)

    // Activity logging and history
    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT 100")
    fun getActivityLogsFlow(): Flow<List<ActivityLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityLog(log: ActivityLogEntity)

    @Query("SELECT COUNT(*) FROM activity_logs WHERE type = :type")
    suspend fun getActivityCount(type: String): Int

    @Query("SELECT COUNT(DISTINCT messageId) FROM activity_logs WHERE type = 'read'")
    suspend fun getUniqueMessagesReadCount(): Int

    // Raw smart searching helper (fallback/direct)
    @Query("""
        SELECT * FROM messages 
        WHERE (:category IS NULL OR category = :category)
        AND (:subcategory IS NULL OR subcategory = :subcategory)
        AND (:language IS NULL OR language = :language)
        AND (:mood IS NULL OR mood = :mood)
        AND (:occasion IS NULL OR occasion = :occasion)
        AND (text LIKE '%' || :query || '%')
        ORDER BY timestamp DESC
    """)
    fun searchMessagesFlow(
        query: String,
        category: String?,
        subcategory: String?,
        language: String?,
        mood: String?,
        occasion: String?
    ): Flow<List<MessageEntity>>
}
