package com.example.ui

import android.content.Context
import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookmarkCollectionEntity
import com.example.data.MessageEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageCard(
    message: MessageEntity,
    fontSizeState: String,
    onFavoriteClick: () -> Unit,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit,
    onAddToCollection: (String?) -> Unit,
    availableCollections: List<BookmarkCollectionEntity>,
    modifier: Modifier = Modifier
) {
    var showCollectionMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val textFontSize = when (fontSizeState) {
        "Small" -> 14.sp
        "Large" -> 20.sp
        else -> 17.sp
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .testTag("message_card_${message.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Info
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Language & Subcategory badges
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    SuggestionChip(
                        onClick = {},
                        label = { Text(text = message.subcategory, fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            labelColor = MaterialTheme.colorScheme.primary
                        ),
                        border = null,
                        modifier = Modifier.height(24.dp)
                    )
                    SuggestionChip(
                        onClick = {},
                        label = { Text(text = message.language, fontSize = 11.sp) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                            labelColor = MaterialTheme.colorScheme.secondary
                        ),
                        border = null,
                        modifier = Modifier.height(24.dp)
                    )
                }

                // AI Generated Badge
                if (message.isCustom) {
                    SuggestionChip(
                        onClick = {},
                        label = { Text("AI ✨", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            labelColor = MaterialTheme.colorScheme.primary
                        ),
                        border = null,
                        modifier = Modifier.height(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Message text itself
            Text(
                text = message.text,
                fontSize = textFontSize,
                lineHeight = textFontSize * 1.4f,
                fontFamily = FontFamily.SansSerif,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Bookmark indicator
                if (message.collectionName != null) {
                    AssistChip(
                        onClick = { onAddToCollection(null) },
                        label = { Text(message.collectionName, fontSize = 11.sp) },
                        leadingIcon = { Icon(Icons.Default.Bookmark, contentDescription = null, size = 12.dp) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.12f),
                            labelColor = MaterialTheme.colorScheme.tertiary
                        )
                    )
                } else {
                    Spacer(modifier = Modifier.width(4.dp))
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Collection manager
                    IconButton(
                        onClick = { showCollectionMenu = true },
                        modifier = Modifier.testTag("collection_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.FolderOpen,
                            contentDescription = "Save to Collection",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                    // Copy button
                    IconButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(message.text))
                            onCopyClick()
                        },
                        modifier = Modifier.testTag("copy_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy message",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                    // Share button
                    IconButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, message.text)
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share love text via"))
                            onShareClick()
                        },
                        modifier = Modifier.testTag("share_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share message",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                    // Favorite button
                    IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier.testTag("favorite_button")
                    ) {
                        Icon(
                            imageVector = if (message.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite toggle",
                            tint = if (message.isFavorite) Color(0xFFFF2D55) else MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }

        // Dropdown menu to assign to bookmark collection
        if (showCollectionMenu) {
            AlertDialog(
                onDismissRequest = { showCollectionMenu = false },
                title = { Text("Select Collection", fontWeight = FontWeight.Bold) },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Save this message to one of your personalized collections to keep them organized.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        // Default "None" to clear
                        ListItem(
                            headlineContent = { Text("Clear from collection") },
                            leadingContent = { Icon(Icons.Default.Clear, contentDescription = null) },
                            modifier = Modifier.clickable {
                                onAddToCollection(null)
                                showCollectionMenu = false
                            }
                        )
                        Divider()

                        availableCollections.forEach { collection ->
                            ListItem(
                                headlineContent = { Text(collection.name) },
                                leadingContent = { 
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(Color(android.graphics.Color.parseColor(collection.colorHex)))
                                    )
                                },
                                modifier = Modifier.clickable {
                                    onAddToCollection(collection.name)
                                    showCollectionMenu = false
                                }
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showCollectionMenu = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

// Icon helper function for dimensions
@Composable
fun Icon(imageVector: ImageVector, contentDescription: String?, size: androidx.compose.ui.unit.Dp) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = Modifier.size(size)
    )
}

@Composable
fun CategoryCapsule(
    name: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .testTag("category_$name")
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = name,
            color = contentColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun MoodSelectorCapsule(
    mood: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val brush = if (isSelected) {
        Brush.horizontalGradient(
            listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.secondary
            )
        )
    } else {
        Brush.horizontalGradient(
            listOf(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val emoji = when (mood) {
        "Happy" -> "😊"
        "Romantic" -> "💘"
        "Funny" -> "🤪"
        "Shy" -> "😳"
        "Confident" -> "😎"
        else -> "🌟"
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(brush)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = "$emoji $mood",
            color = contentColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
