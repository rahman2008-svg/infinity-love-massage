package com.example.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MessageRepository(private val messageDao: MessageDao) {

    val allMessages: Flow<List<MessageEntity>> = messageDao.getAllMessagesFlow()
    val favoriteMessages: Flow<List<MessageEntity>> = messageDao.getFavoriteMessagesFlow()
    val allCollections: Flow<List<BookmarkCollectionEntity>> = messageDao.getAllCollectionsFlow()
    val activityLogs: Flow<List<ActivityLogEntity>> = messageDao.getActivityLogsFlow()

    fun getMessagesByCategory(category: String): Flow<List<MessageEntity>> =
        messageDao.getMessagesByCategoryFlow(category)

    fun getMessagesByCategoryAndSubcategory(category: String, subcategory: String): Flow<List<MessageEntity>> =
        messageDao.getMessagesByCategoryAndSubcategoryFlow(category, subcategory)

    fun getMessagesInCollection(collectionName: String): Flow<List<MessageEntity>> =
        messageDao.getMessagesInCollectionFlow(collectionName)

    suspend fun insertMessage(message: MessageEntity): Long = withContext(Dispatchers.IO) {
        messageDao.insertMessage(message)
    }

    suspend fun updateMessage(message: MessageEntity) = withContext(Dispatchers.IO) {
        messageDao.updateMessage(message)
    }

    suspend fun deleteMessageById(id: Int) = withContext(Dispatchers.IO) {
        messageDao.deleteMessageById(id)
    }

    suspend fun insertCollection(collection: BookmarkCollectionEntity) = withContext(Dispatchers.IO) {
        messageDao.insertCollection(collection)
    }

    suspend fun deleteCollectionByName(name: String) = withContext(Dispatchers.IO) {
        messageDao.deleteCollectionByName(name)
    }

    suspend fun logActivity(type: String, messageId: Int) = withContext(Dispatchers.IO) {
        // Log action (read, copy, share, etc.)
        messageDao.insertActivityLog(ActivityLogEntity(type = type, messageId = messageId))
        
        // Update counts in the message itself
        messageDao.getMessageById(messageId)?.let { message ->
            val updated = when (type) {
                "read" -> message.copy(isRead = true)
                "copy" -> message.copy(copyCount = message.copyCount + 1)
                "share" -> message.copy(shareCount = message.shareCount + 1)
                else -> message
            }
            messageDao.updateMessage(updated)
        }
    }

    suspend fun getUniqueMessagesReadCount(): Int = withContext(Dispatchers.IO) {
        messageDao.getUniqueMessagesReadCount()
    }

    suspend fun getActivityCount(type: String): Int = withContext(Dispatchers.IO) {
        messageDao.getActivityCount(type)
    }

    fun searchMessages(
        query: String,
        category: String? = null,
        subcategory: String? = null,
        language: String? = null,
        mood: String? = null,
        occasion: String? = null
    ): Flow<List<MessageEntity>> {
        return messageDao.searchMessagesFlow(
            query = query,
            category = if (category.isNullOrEmpty() || category == "All") null else category,
            subcategory = if (subcategory.isNullOrEmpty() || subcategory == "All") null else subcategory,
            language = if (language.isNullOrEmpty() || language == "All") null else language,
            mood = if (mood.isNullOrEmpty() || mood == "All") null else mood,
            occasion = if (occasion.isNullOrEmpty() || occasion == "All") null else occasion
        )
    }

    suspend fun preseedDatabaseIfNeeded() = withContext(Dispatchers.IO) {
        val currentCount = messageDao.getMessagesCount()
        if (currentCount < 23000) {
            // Delete old default seeded messages to prevent duplicating/leftovers
            messageDao.deleteAllDefaultMessages()

            // Seed default collections
            MessagePreseedData.defaultCollections.forEach {
                messageDao.insertCollection(it)
            }

            // Chunk insert for safety & performance (chunk size of 2000)
            val chunkedList = MessagePreseedData.initialMessages.chunked(2000)
            chunkedList.forEach { chunk ->
                messageDao.insertMessages(chunk)
            }
        }
    }
}
