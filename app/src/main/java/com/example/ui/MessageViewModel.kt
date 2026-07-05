package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.GeneratedMessage
import com.example.api.RetrofitClient
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class MessageViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = MessageRepository(db.messageDao())

    // --- State: Active Filters ---
    val selectedLanguage = MutableStateFlow("All") // "All", "English", "বাংলা", "हिन्दी", "العربية", "Español", "Français", "Deutsch", "Português", "Italiano"
    val selectedCategory = MutableStateFlow("Pickup Lines") // Active screen category
    val selectedSubcategory = MutableStateFlow("All")
    val selectedMood = MutableStateFlow("All") // "All", "Happy", "Romantic", "Funny", "Shy", "Confident"
    val selectedOccasion = MutableStateFlow("All") // "All", "Birthday", "First Date", "Valentine's Day", "Anniversary"
    val searchQuery = MutableStateFlow("")

    // --- State: Collections & Favorites ---
    val allCollections = repository.allCollections.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val favoriteMessages = repository.favoriteMessages.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // --- State: App Settings ---
    val appTheme = MutableStateFlow("System") // "System", "Light", "Dark"
    val fontSize = MutableStateFlow("Medium") // "Small", "Medium", "Large"
    val appLanguage = MutableStateFlow("English") // Default app interface language

    // --- State: AI Generator ---
    val aiTargetName = MutableStateFlow("")
    val aiRelationship = MutableStateFlow("")
    val aiMood = MutableStateFlow("Romantic")
    val aiOccasion = MutableStateFlow("None")
    val aiLanguage = MutableStateFlow("English")
    val aiKeywords = MutableStateFlow("")
    
    private val _aiGenerationState = MutableStateFlow<AiGenState>(AiGenState.Idle)
    val aiGenerationState: StateFlow<AiGenState> = _aiGenerationState.asStateFlow()

    // --- State: Message Feed List ---
    val filteredMessages = combine(
        selectedCategory,
        selectedSubcategory,
        selectedLanguage,
        selectedMood,
        selectedOccasion,
        searchQuery
    ) { flowsArray ->
        val category = flowsArray[0]
        val subcategory = flowsArray[1]
        val lang = flowsArray[2]
        val mood = flowsArray[3]
        val occasion = flowsArray[4]
        val query = flowsArray[5]
        FilterParams(category, subcategory, lang, mood, occasion, query)
    }.flatMapLatest { params ->
        repository.searchMessages(
            query = params.query,
            category = params.category,
            subcategory = params.subcategory,
            language = params.language,
            mood = params.mood,
            occasion = params.occasion
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // --- State: Achievements and Analytics ---
    private val _copiedCount = MutableStateFlow(0)
    val copiedCount: StateFlow<Int> = _copiedCount.asStateFlow()

    private val _sharedCount = MutableStateFlow(0)
    val sharedCount: StateFlow<Int> = _sharedCount.asStateFlow()

    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    init {
        // Pre-seed database and load analytics
        viewModelScope.launch {
            repository.preseedDatabaseIfNeeded()
            updateAnalytics()
            
            // Set defaults based on app language
            selectedLanguage.value = "All"
        }
    }

    fun updateAnalytics() {
        viewModelScope.launch {
            _copiedCount.value = repository.getActivityCount("copy")
            _sharedCount.value = repository.getActivityCount("share")
            _readCount.value = repository.getUniqueMessagesReadCount()
        }
    }

    // --- Database Operations ---

    fun toggleFavorite(message: MessageEntity) {
        viewModelScope.launch {
            val updated = message.copy(isFavorite = !message.isFavorite)
            repository.updateMessage(updated)
            if (updated.isFavorite) {
                repository.logActivity("favorite", message.id)
            }
        }
    }

    fun markAsRead(message: MessageEntity) {
        if (!message.isRead) {
            viewModelScope.launch {
                repository.logActivity("read", message.id)
                updateAnalytics()
            }
        }
    }

    fun logCopy(messageId: Int) {
        viewModelScope.launch {
            repository.logActivity("copy", messageId)
            updateAnalytics()
        }
    }

    fun logShare(messageId: Int) {
        viewModelScope.launch {
            repository.logActivity("share", messageId)
            updateAnalytics()
        }
    }

    fun addToCollection(message: MessageEntity, collectionName: String?) {
        viewModelScope.launch {
            val updated = message.copy(collectionName = collectionName)
            repository.updateMessage(updated)
        }
    }

    fun createCollection(name: String, colorHex: String, iconName: String) {
        viewModelScope.launch {
            val collection = BookmarkCollectionEntity(name = name, colorHex = colorHex, iconName = iconName)
            repository.insertCollection(collection)
        }
    }

    fun deleteCollection(name: String) {
        viewModelScope.launch {
            repository.deleteCollectionByName(name)
        }
    }

    fun deleteMessage(id: Int) {
        viewModelScope.launch {
            repository.deleteMessageById(id)
        }
    }

    // --- AI Generator Method ---

    fun generateAiMessages() {
        val cat = selectedCategory.value
        val name = aiTargetName.value
        val rel = aiRelationship.value
        val mood = aiMood.value
        val occ = aiOccasion.value
        val lang = aiLanguage.value
        val keys = aiKeywords.value

        _aiGenerationState.value = AiGenState.Loading

        viewModelScope.launch {
            try {
                val results = RetrofitClient.generateCustomMessages(
                    category = cat,
                    targetName = name,
                    relationship = rel,
                    mood = mood,
                    occasion = occ,
                    language = lang,
                    keywordInput = keys
                )

                val entities = results.map { msg ->
                    MessageEntity(
                        text = msg.text,
                        category = cat,
                        subcategory = msg.subcategory,
                        language = lang,
                        mood = msg.mood,
                        occasion = msg.occasion,
                        isCustom = true
                    )
                }

                // Insert into database so they are saved offline instantly
                entities.forEach {
                    val id = repository.insertMessage(it)
                    repository.logActivity("ai_generate", id.toInt())
                }

                _aiGenerationState.value = AiGenState.Success(results)
                updateAnalytics()
            } catch (e: Exception) {
                _aiGenerationState.value = AiGenState.Error(
                    if (e.message == "API_KEY_MISSING") "Please configure your Gemini API Key in the Secrets panel."
                    else e.localizedMessage ?: "Failed to generate messages. Please try again."
                )
            }
        }
    }

    fun clearAiState() {
        _aiGenerationState.value = AiGenState.Idle
    }
}

// --- Supporting Classes ---

data class FilterParams(
    val category: String,
    val subcategory: String,
    val language: String,
    val mood: String,
    val occasion: String,
    val query: String
)

sealed interface AiGenState {
    object Idle : AiGenState
    object Loading : AiGenState
    data class Success(val messages: List<GeneratedMessage>) : AiGenState
    data class Error(val error: String) : AiGenState
}
