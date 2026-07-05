package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.api.GeneratedMessage
import com.example.data.BookmarkCollectionEntity
import com.example.data.MessageEntity
import java.util.UUID


@Composable
fun HomeScreen(
    viewModel: MessageViewModel,
    modifier: Modifier = Modifier
) {
    val messages by viewModel.filteredMessages.collectAsStateWithLifecycle()
    val favorites by viewModel.favoriteMessages.collectAsStateWithLifecycle()
    val collections by viewModel.allCollections.collectAsStateWithLifecycle()
    
    val selectedLang by viewModel.selectedLanguage.collectAsStateWithLifecycle()
    val activeCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val activeSubcategory by viewModel.selectedSubcategory.collectAsStateWithLifecycle()
    val activeMood by viewModel.selectedMood.collectAsStateWithLifecycle()
    val activeOccasion by viewModel.selectedOccasion.collectAsStateWithLifecycle()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()
    val fontSizeState by viewModel.fontSize.collectAsStateWithLifecycle()

    var showCreateCollectionDialog by remember { mutableStateOf(false) }
    var newCollectionName by remember { mutableStateOf("") }
    var newCollectionColor by remember { mutableStateOf("#E91E63") }

    // Define categories
    val categoriesList = listOf(
        CategoryData("Pickup Lines", Icons.Default.Favorite),
        CategoryData("Love Messages", Icons.Default.ChatBubble),
        CategoryData("Conversation Starters", Icons.Default.Sms),
        CategoryData("Compliments", Icons.Default.InsertEmoticon),
        CategoryData("Apology Messages", Icons.Default.SentimentVeryDissatisfied),
        CategoryData("Anniversary Wishes", Icons.Default.Cake),
        CategoryData("Favorites", Icons.Default.Star),
        CategoryData("Bookmark Collections", Icons.Default.Folder),
        CategoryData("History", Icons.Default.History)
    )

    // Dynamic subcategories mapping
    val subcategoriesMap = mapOf(
        "Pickup Lines" to listOf("All", "Funny", "Cute", "Romantic", "Sweet", "Cheesy", "Nerdy", "Gaming", "Movie", "Coffee", "Valentine's Day"),
        "Love Messages" to listOf("All", "Good Morning", "Good Night", "Missing You", "Thinking of You", "I Love You", "Appreciation", "Short Love Texts"),
        "Conversation Starters" to listOf("All", "First Message", "First Date", "Online Dating", "Long Distance", "Friends", "Relationship", "Ice Breakers"),
        "Compliments" to listOf("All", "Beauty", "Smile", "Eyes", "Personality", "Intelligence", "Kindness", "Confidence"),
        "Apology Messages" to listOf("All", "Sorry Messages", "Forgiveness Quotes", "Relationship Repair"),
        "Anniversary Wishes" to listOf("All", "Birthday", "Anniversary", "Engagement", "Wedding", "Congratulations")
    )

    val currentSubcategories = subcategoriesMap[activeCategory] ?: emptyList()

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // --- 1. Search Bar ---
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.searchQuery.value = it },
            placeholder = { Text("Smart Search by keyword, emotion or occasion...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { viewModel.searchQuery.value = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear search")
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("search_bar")
        )

        // --- Beautiful Hero Banner ---
        if (query.isEmpty() && activeCategory == "Pickup Lines" && activeSubcategory == "All") {
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = com.example.R.drawable.img_love_hero_banner_1783233145932),
                        contentDescription = "Sophisticated romantic banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Gradient overlay to keep it beautifully integrated
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xFF1A1112).copy(alpha = 0.85f))
                                )
                            )
                    )
                    // Elegant text on top
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "Express Your Feelings",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Premium dark-curated romantic messages & pickup lines",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.75f)
                        )
                    }
                }
            }
        }

        // --- Daily Pick Card (Featured) ---
        var showDailyPick by remember { mutableStateOf(true) }
        if (showDailyPick && query.isEmpty() && activeCategory == "Pickup Lines" && activeSubcategory == "All") {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFFFB2BE).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(24.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF561E26), Color(0xFF3D2E2F))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Featured Pickup Line",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFB2BE)
                            )
                            
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFFFB2BE))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    "DAILY PICK",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF561E26)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            "\"Are you a magician? Because whenever I look at you, everyone else disappears.\"",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            color = Color.White,
                            lineHeight = 24.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    "EN",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White.copy(alpha = 0.6f)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.4f))
                                )
                                Text(
                                    "ROMANTIC",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White.copy(alpha = 0.6f)
                                )
                            }
                            
                            val clipboardManager = androidx.compose.ui.platform.LocalClipboardManager.current
                            Button(
                                onClick = {
                                    clipboardManager.setText(androidx.compose.ui.text.AnnotatedString("Are you a magician? Because whenever I look at you, everyone else disappears."))
                                    android.widget.Toast.makeText(context, "Copied featured pickup line!", android.widget.Toast.LENGTH_SHORT).show()
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White.copy(alpha = 0.1f),
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Text("Copy Message", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }

        // --- 2. Smart Horizontal Filters Panel ---
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            // Language Selection Filter
            item {
                var showLangMenu by remember { mutableStateOf(false) }
                FilterChip(
                    selected = selectedLang != "All",
                    onClick = { showLangMenu = true },
                    label = { Text(if (selectedLang == "All") "Language: All" else "Language: $selectedLang") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null, size = 16.dp) }
                )

                DropdownMenu(
                    expanded = showLangMenu,
                    onDismissRequest = { showLangMenu = false }
                ) {
                    listOf("All", "English", "বাংলা", "हिन्दी", "العربية", "Español", "Français", "Deutsch", "Português", "Italiano").forEach { lang ->
                        DropdownMenuItem(
                            text = { Text(lang) },
                            onClick = {
                                viewModel.selectedLanguage.value = lang
                                showLangMenu = false
                            }
                        )
                    }
                }
            }

            // Mood Filter (Dynamic Row Selection)
            item {
                var showMoodMenu by remember { mutableStateOf(false) }
                FilterChip(
                    selected = activeMood != "All",
                    onClick = { showMoodMenu = true },
                    label = { Text(if (activeMood == "All") "Mood: All" else "Mood: $activeMood") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null, size = 16.dp) }
                )

                DropdownMenu(
                    expanded = showMoodMenu,
                    onDismissRequest = { showMoodMenu = false }
                ) {
                    listOf("All", "Happy", "Romantic", "Funny", "Shy", "Confident").forEach { mood ->
                        DropdownMenuItem(
                            text = { Text(mood) },
                            onClick = {
                                viewModel.selectedMood.value = mood
                                showMoodMenu = false
                            }
                        )
                    }
                }
            }

            // Occasion Filter
            item {
                var showOccasionMenu by remember { mutableStateOf(false) }
                FilterChip(
                    selected = activeOccasion != "All",
                    onClick = { showOccasionMenu = true },
                    label = { Text(if (activeOccasion == "All") "Occasion: All" else "Occasion: $activeOccasion") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null, size = 16.dp) }
                )

                DropdownMenu(
                    expanded = showOccasionMenu,
                    onDismissRequest = { showOccasionMenu = false }
                ) {
                    listOf("All", "Birthday", "First Date", "Valentine's Day", "Anniversary").forEach { occ ->
                        DropdownMenuItem(
                            text = { Text(occ) },
                            onClick = {
                                viewModel.selectedOccasion.value = occ
                                showOccasionMenu = false
                            }
                        )
                    }
                }
            }
        }

        // --- 3. Main Categories Scrollable Row ---
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categoriesList) { cat ->
                CategoryCapsule(
                    name = cat.name,
                    icon = cat.icon,
                    isSelected = activeCategory == cat.name,
                    onClick = {
                        viewModel.selectedCategory.value = cat.name
                        viewModel.selectedSubcategory.value = "All" // Reset subcategory on main change
                    }
                )
            }
        }

        // --- 4. Subcategories Scrollable Bar ---
        if (currentSubcategories.isNotEmpty()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
                    .padding(vertical = 4.dp)
            ) {
                items(currentSubcategories) { sub ->
                    val isSel = activeSubcategory == sub
                    SuggestionChip(
                        onClick = { viewModel.selectedSubcategory.value = sub },
                        label = { Text(sub, fontSize = 12.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = if (isSel) MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f) else Color.Transparent,
                            labelColor = if (isSel) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        ),
                        border = null
                    )
                }
            }
        }

        // --- 5. Message List View ---
        Box(modifier = Modifier.weight(1f)) {
            when (activeCategory) {
                "Bookmark Collections" -> {
                    // Manage Custom Bookmark Collections
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "My Custom Collections",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Button(
                                onClick = { showCreateCollectionDialog = true },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, size = 16.dp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("New")
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (collections.isEmpty()) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        Icons.Default.FolderOpen,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                        modifier = Modifier.size(56.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "No collections created yet.",
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                    )
                                }
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                items(collections) { col ->
                                    val count = messages.count { it.collectionName == col.name }

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                viewModel.selectedCategory.value = "Bookmark"
                                                viewModel.searchQuery.value = "" // Reset search
                                                // Load only the collection
                                                viewModel.selectedSubcategory.value = col.name
                                            },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(36.dp)
                                                        .clip(CircleShape)
                                                        .background(Color(android.graphics.Color.parseColor(col.colorHex)))
                                                )
                                                Spacer(modifier = Modifier.width(12.dp))
                                                Column {
                                                    Text(col.name, fontWeight = FontWeight.Bold)
                                                    Text("$count messages saved", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                                                }
                                            }

                                            IconButton(onClick = { viewModel.deleteCollection(col.name) }) {
                                                Icon(Icons.Default.Delete, contentDescription = "Delete collection", tint = Color.Gray)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                "Bookmark" -> {
                    // Displaying messages inside a selected collection
                    val collectionName = activeSubcategory
                    val messagesInCol = messages.filter { it.collectionName == collectionName }
                    
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            IconButton(onClick = { viewModel.selectedCategory.value = "Bookmark Collections" }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Collection: $collectionName", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        }

                        if (messagesInCol.isEmpty()) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Text("No messages saved in this collection yet.")
                            }
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(16.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(messagesInCol) { message ->
                                    LaunchedEffect(message.id) {
                                        viewModel.markAsRead(message)
                                    }
                                    MessageCard(
                                        message = message,
                                        fontSizeState = fontSizeState,
                                        onFavoriteClick = { viewModel.toggleFavorite(message) },
                                        onCopyClick = { viewModel.logCopy(message.id) },
                                        onShareClick = { viewModel.logShare(message.id) },
                                        onAddToCollection = { col -> viewModel.addToCollection(message, col) },
                                        availableCollections = collections
                                    )
                                }
                            }
                        }
                    }
                }

                "Favorites" -> {
                    if (favorites.isEmpty()) {
                        EmptyStateView("You haven't saved any favorites yet. Tap the heart icon on any message card!")
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(favorites) { message ->
                                LaunchedEffect(message.id) {
                                    viewModel.markAsRead(message)
                                }
                                MessageCard(
                                    message = message,
                                    fontSizeState = fontSizeState,
                                    onFavoriteClick = { viewModel.toggleFavorite(message) },
                                    onCopyClick = { viewModel.logCopy(message.id) },
                                    onShareClick = { viewModel.logShare(message.id) },
                                    onAddToCollection = { col -> viewModel.addToCollection(message, col) },
                                    availableCollections = collections
                                )
                            }
                        }
                    }
                }

                "History" -> {
                    // Filter messages with read, copy or share counts > 0
                    val historyMessages = messages.filter { it.isRead || it.copyCount > 0 || it.shareCount > 0 }

                    if (historyMessages.isEmpty()) {
                        EmptyStateView("Your message activity history is empty. Try copying, sharing or generating messages!")
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(historyMessages) { message ->
                                MessageCard(
                                    message = message,
                                    fontSizeState = fontSizeState,
                                    onFavoriteClick = { viewModel.toggleFavorite(message) },
                                    onCopyClick = { viewModel.logCopy(message.id) },
                                    onShareClick = { viewModel.logShare(message.id) },
                                    onAddToCollection = { col -> viewModel.addToCollection(message, col) },
                                    availableCollections = collections
                                )
                            }
                        }
                    }
                }

                else -> {
                    // General main category views (e.g. Pickup lines, Love messages)
                    if (messages.isEmpty()) {
                        EmptyStateView(
                            message = "No messages found for the selected filters. Use the AI Generator tab to write some customized messages instantly!"
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(messages) { message ->
                                LaunchedEffect(message.id) {
                                    viewModel.markAsRead(message)
                                }
                                MessageCard(
                                    message = message,
                                    fontSizeState = fontSizeState,
                                    onFavoriteClick = { viewModel.toggleFavorite(message) },
                                    onCopyClick = { viewModel.logCopy(message.id) },
                                    onShareClick = { viewModel.logShare(message.id) },
                                    onAddToCollection = { col -> viewModel.addToCollection(message, col) },
                                    availableCollections = collections
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // New Collection popup dialog
    if (showCreateCollectionDialog) {
        AlertDialog(
            onDismissRequest = { showCreateCollectionDialog = false },
            title = { Text("Create Custom Collection", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    OutlinedTextField(
                        value = newCollectionName,
                        onValueChange = { newCollectionName = it },
                        label = { Text("Collection Name") },
                        placeholder = { Text("e.g., Flirty Texts, Deep Love") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Pick Theme Color", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                    
                    Spacer(modifier = Modifier.height(8.dp))

                    val colorsList = listOf("#E91E63", "#00F5D4", "#9C27B0", "#FF9800", "#4CAF50", "#2196F3")
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(colorsList) { hex ->
                            val isSel = newCollectionColor == hex
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color(android.graphics.Color.parseColor(hex)))
                                    .clickable { newCollectionColor = hex }
                                    .border(
                                        width = if (isSel) 3.dp else 0.dp,
                                        color = if (isSel) MaterialTheme.colorScheme.onSurface else Color.Transparent,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newCollectionName.isNotEmpty()) {
                            viewModel.createCollection(
                                name = newCollectionName,
                                colorHex = newCollectionColor,
                                iconName = "folder"
                            )
                            newCollectionName = ""
                            showCreateCollectionDialog = false
                        }
                    }
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateCollectionDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun AiGeneratorScreen(
    viewModel: MessageViewModel,
    modifier: Modifier = Modifier
) {
    val aiState by viewModel.aiGenerationState.collectAsStateWithLifecycle()
    val collections by viewModel.allCollections.collectAsStateWithLifecycle()

    val targetName by viewModel.aiTargetName.collectAsStateWithLifecycle()
    val relationship by viewModel.aiRelationship.collectAsStateWithLifecycle()
    val aiMood by viewModel.aiMood.collectAsStateWithLifecycle()
    val aiOccasion by viewModel.aiOccasion.collectAsStateWithLifecycle()
    val aiLanguage by viewModel.aiLanguage.collectAsStateWithLifecycle()
    val aiKeywords by viewModel.aiKeywords.collectAsStateWithLifecycle()
    val fontSizeState by viewModel.fontSize.collectAsStateWithLifecycle()

    val activeCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            // Header
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Text(
                        "✨ Custom Love Writer",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Generate highly tailored love messages, pickup lines or wishes instantly offline with our premium synthesis engine.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Customization Options", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                    // Target Category Indicator
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Category to Generate", style = MaterialTheme.typography.bodyMedium)
                        AssistChip(
                            onClick = {},
                            label = { Text(activeCategory) },
                            colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        )
                    }

                    Divider()

                    // Target Name
                    OutlinedTextField(
                        value = targetName,
                        onValueChange = { viewModel.aiTargetName.value = it },
                        label = { Text("Recipient's Name (Optional)") },
                        placeholder = { Text("e.g. Priyo, Aisha, John") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Relationship
                    OutlinedTextField(
                        value = relationship,
                        onValueChange = { viewModel.aiRelationship.value = it },
                        label = { Text("Relationship / Context") },
                        placeholder = { Text("e.g. Crush, Wife, Boyfriend, Secret Admirer") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Keywords input
                    OutlinedTextField(
                        value = aiKeywords,
                        onValueChange = { viewModel.aiKeywords.value = it },
                        label = { Text("Custom Prompt / Keywords (Optional)") },
                        placeholder = { Text("e.g., loves cats, works at office, coffee addict") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Lang Selection
                    var langExpanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = aiLanguage,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Message Language") },
                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { langExpanded = true }
                        )
                        DropdownMenu(
                            expanded = langExpanded,
                            onDismissRequest = { langExpanded = false }
                        ) {
                            listOf("English", "বাংলা", "हिन्दी", "العربية", "Español", "Français", "Deutsch", "Português", "Italiano").forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text(lang) },
                                    onClick = {
                                        viewModel.aiLanguage.value = lang
                                        langExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Mood selector
                    var moodExpanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = aiMood,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Tone / Mood") },
                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { moodExpanded = true }
                        )
                        DropdownMenu(
                            expanded = moodExpanded,
                            onDismissRequest = { moodExpanded = false }
                        ) {
                            listOf("Romantic", "Funny", "Happy", "Shy", "Confident").forEach { mood ->
                                DropdownMenuItem(
                                    text = { Text(mood) },
                                    onClick = {
                                        viewModel.aiMood.value = mood
                                        moodExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Occasion Selector
                    var occExpanded by remember { mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = aiOccasion,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Occasion") },
                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { occExpanded = true }
                        )
                        DropdownMenu(
                            expanded = occExpanded,
                            onDismissRequest = { occExpanded = false }
                        ) {
                            listOf("None", "Birthday", "Valentine's Day", "First Date", "Anniversary").forEach { occ ->
                                DropdownMenuItem(
                                    text = { Text(occ) },
                                    onClick = {
                                        viewModel.aiOccasion.value = occ
                                        occExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Generation button
                    Button(
                        onClick = { viewModel.generateAiMessages() },
                        shape = RoundedCornerShape(14.dp),
                        enabled = aiState !is AiGenState.Loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("generate_button")
                    ) {
                        if (aiState is AiGenState.Loading) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate 5 Custom Messages", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                }
            }
        }

        // Output Display depending on AI state
        when (val state = aiState) {
            is AiGenState.Loading -> {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Gemini is composing custom texts...", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            is AiGenState.Error -> {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Error, "Error", tint = MaterialTheme.colorScheme.error)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Generation Error", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(state.error, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onErrorContainer)
                        }
                    }
                }
            }

            is AiGenState.Success -> {
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        Text("Generated Results (Saved Offline)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        TextButton(onClick = { viewModel.clearAiState() }) {
                            Text("Clear")
                        }
                    }
                }

                items(state.messages) { msg ->
                    val messageEntity = MessageEntity(
                        id = UUID.randomUUID().hashCode(),
                        text = msg.text,
                        category = activeCategory,
                        subcategory = msg.subcategory,
                        language = aiLanguage,
                        mood = msg.mood,
                        occasion = msg.occasion,
                        isCustom = true
                    )

                    MessageCard(
                        message = messageEntity,
                        fontSizeState = fontSizeState,
                        onFavoriteClick = { viewModel.toggleFavorite(messageEntity) },
                        onCopyClick = { viewModel.logCopy(messageEntity.id) },
                        onShareClick = { viewModel.logShare(messageEntity.id) },
                        onAddToCollection = { col -> viewModel.addToCollection(messageEntity, col) },
                        availableCollections = collections
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
fun AchievementsScreen(
    viewModel: MessageViewModel,
    modifier: Modifier = Modifier
) {
    val copies by viewModel.copiedCount.collectAsStateWithLifecycle()
    val shares by viewModel.sharedCount.collectAsStateWithLifecycle()
    val reads by viewModel.readCount.collectAsStateWithLifecycle()
    val collections by viewModel.allCollections.collectAsStateWithLifecycle()

    val completedExplorer = reads >= 10
    val completedWordsmith = copies >= 10
    val completedCupid = shares >= 15
    val completedCollections = collections.size >= 2

    LaunchedEffect(Unit) {
        viewModel.updateAnalytics()
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Text("Cupid Dashboard & Badges", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Track your reading and sharing activity to unlock beautiful romantic badges.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        }

        // Stats summary row
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                StatCard("📖 Unique Read", "$reads", Modifier.weight(1f))
                StatCard("📋 Copied", "$copies", Modifier.weight(1f))
                StatCard("📤 Shared", "$shares", Modifier.weight(1f))
            }
        }

        item {
            Text("My Badges", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
        }

        // Achievements / badges list
        item {
            AchievementBadgeRow(
                title = "Romantic Explorer",
                desc = "Read at least 10 different love texts or pickup lines.",
                current = reads,
                target = 10,
                isCompleted = completedExplorer,
                badgeColor = Color(0xFFFF2D55)
            )
        }

        item {
            AchievementBadgeRow(
                title = "Wordsmith Apprentice",
                desc = "Copy at least 10 love messages to your clipboard.",
                current = copies,
                target = 10,
                isCompleted = completedWordsmith,
                badgeColor = Color(0xFFFF9500)
            )
        }

        item {
            AchievementBadgeRow(
                title = "Messenger of Cupid",
                desc = "Share at least 15 messages via WhatsApp, Messenger or SMS.",
                current = shares,
                target = 15,
                isCompleted = completedCupid,
                badgeColor = Color(0xFF5856D6)
            )
        }

        item {
            AchievementBadgeRow(
                title = "Lover of Collections",
                desc = "Organize your messages by creating at least 2 custom bookmark collections.",
                current = collections.size,
                target = 2,
                isCompleted = completedCollections,
                badgeColor = Color(0xFFAF52DE)
            )
        }
    }
}

@Composable
fun SettingsScreen(
    viewModel: MessageViewModel,
    modifier: Modifier = Modifier
) {
    val themeState by viewModel.appTheme.collectAsStateWithLifecycle()
    val fontSizeState by viewModel.fontSize.collectAsStateWithLifecycle()
    val appLangState by viewModel.appLanguage.collectAsStateWithLifecycle()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            Text("Settings & Customization", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }

        // Settings list
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Visual Settings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Theme selector
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("App Theme", fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("System", "Light", "Dark").forEach { t ->
                                val isSel = themeState == t
                                FilterChip(
                                    selected = isSel,
                                    onClick = { viewModel.appTheme.value = t },
                                    label = { Text(t) }
                                )
                            }
                        }
                    }

                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    // Font size selector
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Font Size", fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("Small", "Medium", "Large").forEach { size ->
                                val isSel = fontSizeState == size
                                FilterChip(
                                    selected = isSel,
                                    onClick = { viewModel.fontSize.value = size },
                                    label = { Text(size) }
                                )
                            }
                        }
                    }
                }
            }
        }

        // About Developer Section
        item {
            val uriHandler = androidx.compose.ui.platform.LocalUriHandler.current
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BoxBorderBrush()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Code,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                "About Developer",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                "Prince AR Abdur Rahman",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Independent App Developer passionate about building modern Android applications, productivity tools, AI-powered experiences, media players, educational apps, and next-generation digital products.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Connect & Contact:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Contact Links
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { uriHandler.openUri("https://wa.me/8801707424006") }
                                .padding(vertical = 4.dp)
                        ) {
                            Text("💬", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "WhatsApp: 01707424006",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { uriHandler.openUri("https://wa.me/8801796951709") }
                                .padding(vertical = 4.dp)
                        ) {
                            Text("💬", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "WhatsApp: 01796951709",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { uriHandler.openUri("https://www.facebook.com/share/1BNn32qoJo/") }
                                .padding(vertical = 4.dp)
                        ) {
                            Text("🌐", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "Facebook Profile",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { uriHandler.openUri("https://www.instagram.com/ur___abdur____rahman__2008") }
                                .padding(vertical = 4.dp)
                        ) {
                            Text("📸", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                "Instagram Profile",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // About Company Section
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BoxBorderBrush()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Business,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                "About Company",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                "NexVora Lab's Ofc",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "NexVora Lab's Ofc focuses on creating innovative Android applications designed to improve productivity, entertainment, learning, and digital experiences.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Mission:",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        "Build fast, beautiful, privacy-friendly, and user-focused applications accessible to everyone.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Credits & Tech Info Section
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        "Technical Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("App Name", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Text("Infinity Love Massage", fontWeight = FontWeight.Bold)
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Version", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Text("1.0.0", fontWeight = FontWeight.Bold)
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Database", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Text("Local Offline (24,000+ Items)", fontWeight = FontWeight.Bold)
                    }

                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    Text(
                        "Credits",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Developed by Prince AR Abdur Rahman\nPublished by NexVora Lab's Ofc",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "© 2026 NexVora Lab's Ofc. All Rights Reserved.",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// Helper views

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Text(title, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun AchievementBadgeRow(
    title: String,
    desc: String,
    current: Int,
    target: Int,
    isCompleted: Boolean,
    badgeColor: Color
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) badgeColor.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (isCompleted) badgeColor else Color.LightGray.copy(alpha = 0.5f))
            ) {
                Icon(
                    imageVector = if (isCompleted) Icons.Default.EmojiEvents else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (isCompleted) Color.White else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = if (isCompleted) badgeColor else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = desc,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LinearProgressIndicator(
                        progress = { (current.toFloat() / target.toFloat()).coerceIn(0f, 1f) },
                        color = badgeColor,
                        trackColor = Color.LightGray.copy(alpha = 0.3f),
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "$current/$target",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStateView(message: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun BoxBorderBrush(): androidx.compose.foundation.BorderStroke {
    return androidx.compose.foundation.BorderStroke(
        width = 1.dp,
        brush = Brush.horizontalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
            )
        )
    )
}

data class CategoryData(val name: String, val icon: ImageVector)
