package com.example.api

import com.example.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

// --- Request Data Classes ---

@JsonClass(generateAdapter = true)
data class GeminiPart(
    @Json(name = "text") val text: String
)

@JsonClass(generateAdapter = true)
data class GeminiContent(
    @Json(name = "parts") val parts: List<GeminiPart>
)

@JsonClass(generateAdapter = true)
data class GeminiGenerationConfig(
    @Json(name = "temperature") val temperature: Double? = 0.7,
    @Json(name = "responseMimeType") val responseMimeType: String? = "application/json"
)

@JsonClass(generateAdapter = true)
data class GeminiRequest(
    @Json(name = "contents") val contents: List<GeminiContent>,
    @Json(name = "generationConfig") val generationConfig: GeminiGenerationConfig? = null,
    @Json(name = "systemInstruction") val systemInstruction: GeminiContent? = null
)

// --- Response Data Classes ---

@JsonClass(generateAdapter = true)
data class GeminiPartResponse(
    @Json(name = "text") val text: String?
)

@JsonClass(generateAdapter = true)
data class GeminiContentResponse(
    @Json(name = "parts") val parts: List<GeminiPartResponse>?
)

@JsonClass(generateAdapter = true)
data class GeminiCandidate(
    @Json(name = "content") val content: GeminiContentResponse?
)

@JsonClass(generateAdapter = true)
data class GeminiResponse(
    @Json(name = "candidates") val candidates: List<GeminiCandidate>?
)

// --- Retrofit Interface ---

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

// --- Moshi parsed custom message class ---
@JsonClass(generateAdapter = true)
data class GeneratedMessage(
    @Json(name = "text") val text: String,
    @Json(name = "subcategory") val subcategory: String,
    @Json(name = "mood") val mood: String,
    @Json(name = "occasion") val occasion: String
)

@JsonClass(generateAdapter = true)
data class GeneratedMessageList(
    @Json(name = "messages") val messages: List<GeneratedMessage>
)

// --- Retrofit Client ---

object RetrofitClient {
    suspend fun generateCustomMessages(
        category: String,
        targetName: String,
        relationship: String,
        mood: String,
        occasion: String,
        language: String,
        keywordInput: String
    ): List<GeneratedMessage> {
        val name = targetName.ifEmpty { if (language == "বাংলা") "প্রিয়" else "my love" }
        val keyword = if (keywordInput.isNotEmpty()) {
            " ($keywordInput)"
        } else ""

        val list = mutableListOf<GeneratedMessage>()

        if (language == "বাংলা") {
            val templates = when (category) {
                "Pickup Lines" -> listOf(
                    "$name, আপনি কি কোনো জাদু জানেন? কারণ যখনই আমি আপনার দিকে তাকাই, চারপাশের সবাই উধাও হয়ে যায়।$keyword",
                    "$name, আমার ফোনে কোনো ম্যাপ কাজ করছে না, কারণ আমি আপনার চোখের মাঝে হারিয়ে গেছি।$keyword",
                    "$name, আপনি কি প্রথম দেখায় ভালোবাসায় বিশ্বাস করেন, নাকি আমি আরেকবার আপনার সামনে দিয়ে হেঁটে যাবো?$keyword",
                    "চা নাকি কফি? কোনটা আপনার বেশি পছন্দ $name? নাকি আপনি আমার সাথে একটা সুন্দর বিকেল কাটাবেন?$keyword",
                    "শুনুন $name, আপনি দেখতে এতটাই সুন্দর যে আপনাকে দেখলে আমার হৃদস্পন্দন বেড়ে যায়!$keyword"
                )
                "Love Messages" -> listOf(
                    "$name, শুভ সকাল আমার ভালোবাসার মানুষটিকে। তোমার মিষ্টি হাসি দিয়ে আমার আজকের দিনটি চমৎকার শুরু হোক!$keyword",
                    "ঘুমোতে যাওয়ার আগে শুধু একটা কথাই মনে করিয়ে দিতে চাই $name: তুমি আমার জীবনের সবচেয়ে বড় প্রাপ্তি। শুভ রাত্রি!$keyword",
                    "$name, তোমাকে কতটা মিস করি তা যদি আকাশের তারাগুলো জানতো, তবে তারাও আজ আমার সাথে রাত জাগতো।$keyword",
                    "$name, আমার জীবনের প্রতিটি খুশির মুহূর্তের পেছনে শুধু তুমিই আছো। আমি তোমাকে অনেক বেশি ভালোবাসি।$keyword",
                    "তোমার ওই মায়াবী চাহনি আর মিষ্টি কথা $name, আমাকে প্রতি মুহূর্তে তোমার প্রেমে পড়তে বাধ্য করে।$keyword"
                )
                "Romantic Quotes" -> listOf(
                    "প্রেমের শক্তি এতটাই গভীর যে তা $name-কে আমার হৃদয়ের সবচেয়ে কাছে নিয়ে আসে। শুভ কামনা!$keyword",
                    "$name, চাঁদের জোছনা আলোয় পরিষ্কার বোঝা যায় যে, ভালোবাসা নিখুঁত মানুষ খোঁজা নয়, বরং একে অপরকে বুঝতে পারা।$keyword",
                    "তোমার সাথে কাটানো প্রতিটি মুহূর্ত স্বর্গের চেয়েও সুন্দর $name, তুমি আমার বেঁচে থাকার পরম সার্থকতা।$keyword",
                    "দুটি হৃদয়ের একই সাথে স্পন্দিত হওয়া জয় করতে পারে মহাবিশ্বের যেকোনো কঠিনতম বাধা, প্রিয় $name।$keyword",
                    "আমাদের এই সুন্দর বন্ধন আকাশের তারায় লেখা ছিল, যেখানে তুমি আর আমি আজীবন একসাথে চলবো $name।$keyword"
                )
                "Conversation Starters" -> listOf(
                    "$name, আপনি যদি কখনো বিশ্বের যেকোনো জায়গায় ভ্রমণ করতে পারতেন, তবে কোন জায়গাটি আগে বেছে নিতেন?$keyword",
                    "যদি আপনাকে সারাদিন নিজের ইচ্ছেমতো যেকোনো কাজ করতে বলা হতো, তবে আপনি কী করতেন $name?$keyword",
                    "আপনার জীবনের সবচেয়ে মজার বা সেরা স্মৃতিটি কী $name? আমি শুনতে ভীষণ আগ্রহী।$keyword",
                    "$name, আপনি কি বরং কোনো অলৌকিক ক্ষমতা পেতে চাইবেন নাকি সাধারণ কিন্তু সুন্দর একটি জীবন পছন্দ করবেন?$keyword",
                    "এক কাপ কফি খেতে খেতে আপনার প্রিয় বিষয়ে আড্ডা দেওয়া যাক $name?$keyword"
                )
                "Compliments" -> listOf(
                    "$name, আপনার মাঝে সবচেয়ে আকর্ষণীয় এক মিষ্টি হাসি আছে যা আমার পুরো দিনটিকে উজ্জ্বল করে তোলে।$keyword",
                    "আমি আপনার অসাধারণ এক ইতিবাচক শক্তিকে ভীষণ পছন্দ করি $name, যা সবাইকে ভালো অনুভূতি দেয়।$keyword",
                    "$name, আপনার মনটা এত সুন্দর ও দয়ালু যে তা আমাকে একজন ভালো মানুষ হতে অনুপ্রাণিত করে।$keyword",
                    "আপনার অপূর্ব মায়াবী দুটি চোখ $name, আমাকে সবসময় মুগ্ধ ও নির্বাক করে দেয়।$keyword",
                    "আপনার অনন্য চিন্তাভাবনা এবং ব্যক্তিত্ব সত্যিই প্রশংসনীয় $name, আপনি অসাধারণ!$keyword"
                )
                "Apology Messages" -> listOf(
                    "$name, আমি সত্যিই অন্তর থেকে ক্ষমাপ্রার্থী আপনার সুন্দর অনুভূতিগুলোকে আঘাত করার জন্য। দয়া করে ক্ষমা করে দিন।$keyword",
                    "আমার না ভেবে বলা কিছু বোকা কথার জন্য আমি অত্যন্ত দুঃখিত $name, প্লিজ রাগ ভুলে মিষ্টি করে তাকান।$keyword",
                    "$name, আমাদের মাঝে এই অপ্রয়োজনীয় দূরত্ব আমাকে কষ্ট দিচ্ছে। আমার ভুলগুলো প্লিজ ক্ষমা সুন্দর দৃষ্টিতে দেখবেন।$keyword",
                    "আপনার চোখে পানি আনার জন্য আমি ক্ষমা চাচ্ছি $name, আপনার হাসিটাই আমার কাছে সবচেয়ে গুরুত্বপূর্ণ।$keyword",
                    "$name, আমি নিজেকে শুধরে নেওয়ার অঙ্গীকার করছি। প্লিজ আমাদের সুন্দর সম্পর্কটির জন্য আমাকে ক্ষমা করুন।$keyword"
                )
                else -> listOf( // Breakup Messages or fallback
                    "আমার হৃদয় ভেঙে যায় এটা ভেবে যে, $name, আমাদের চলার পথ দুটি ভিন্ন দিকে চলে গেছে।$keyword",
                    "আমাদের মেনে নিতেই হবে যে আমরা আর আগের মতো সেই খুশি খুঁজে পাচ্ছি না, ভালো থাকবেন $name।$keyword",
                    "$name, ধরে রাখার চেয়ে ছেড়ে দেওয়া অনেক কম বেদনাদায়ক। আমি আপনার মঙ্গল কামনা করি।$keyword",
                    "চেষ্টা করার পরেও আমরা একে অপরের থেকে দূরে সরে যাচ্ছি $name, আপনার ভবিষ্যৎ সুন্দর ও শান্তিময় হোক।$keyword",
                    "$name, আমাদের সুন্দর অধ্যায়টির এখানেই সমাপ্তি টানা প্রয়োজন। বিদায় এবং ভালো থাকবেন।$keyword"
                )
            }

            templates.forEach { text ->
                list.add(
                    GeneratedMessage(
                        text = text,
                        subcategory = if (category == "Pickup Lines") "Cute" else "Romantic",
                        mood = mood,
                        occasion = occasion
                    )
                )
            }
        } else {
            // English or other languages
            val templates = when (category) {
                "Pickup Lines" -> listOf(
                    "Are you a magician, $name? Because whenever I look at you, everyone else disappears.$keyword",
                    "Excuse me, $name, but do you have a map? Because I keep getting lost in your eyes.$keyword",
                    "$name, do you believe in love at first sight, or should I walk by again?$keyword",
                    "Are you made of copper and tellurium, $name? Because you're CUtE.$keyword",
                    "I'm not a photographer, $name, but I can easily picture us together forever.$keyword"
                )
                "Love Messages" -> listOf(
                    "Good morning to $name, the one who makes my world spin. Your smile is my sunshine today!$keyword",
                    "Just a little reminder before you sleep, $name: you are loved, you are appreciated, and you are my everything. Good night!$keyword",
                    "I miss you like the stars miss the sun in the morning, $name. Can't wait to hold you again.$keyword",
                    "$name, if I had a flower for every time I thought of you, I could walk through my garden forever.$keyword",
                    "You are the best thing that ever happened to me, $name, my anchor in the storm and my joy in the calm. I love you!$keyword"
                )
                "Romantic Quotes" -> listOf(
                    "In the book of destiny, loving $name is as natural as breathing, and our journey is an endless beautiful sky.$keyword",
                    "True romance is a quiet flame that burns brighter than the sun when I'm with you, $name.$keyword",
                    "$name, every look you give me is a masterpiece because it captures the pure beauty of your soul.$keyword",
                    "To find someone like $name who knows your silent thoughts is to feel the warmth of heaven itself.$keyword",
                    "$name, our connection was written in the stars, where two souls reside in the same sweet space.$keyword"
                )
                "Conversation Starters" -> listOf(
                    "If you could travel to any place in the world right now, $name, where would you choose first?$keyword",
                    "What is your idea of a perfect day, $name? I'd love to know more about you!$keyword",
                    "If you could have any superpower for 24 hours, $name, what would it be and why?$keyword",
                    "$name, what is one thing that always makes you smile and brings back the sweetest feelings?$keyword",
                    "Would you rather have a deep conversation over coffee, $name, or explore a new city together?$keyword"
                )
                "Compliments" -> listOf(
                    "$name, your smile has the power to light up even the darkest of days. Never lose it!$keyword",
                    "I love the way your eyes sparkle, $name, especially when you talk about things you are passionate about.$keyword",
                    "$name, your kindness is a breath of fresh air in this world. You inspire me to be a better person.$keyword",
                    "You have such a lovely sense of humor, $name, it brings so much joy and warmth to my life.$keyword",
                    "$name, you have an incredible energy and a unique way of thinking that is truly captivating.$keyword"
                )
                "Apology Messages" -> listOf(
                    "I am deeply sorry for hurting you, $name. Your happiness means everything to me, and I promise to do better.$keyword",
                    "Please forgive me, $name. Our relationship is too precious to lose over a silly misunderstanding.$keyword",
                    "$name, my heart is heavy because of the distance between us. I am truly sorry and want to make things right.$keyword",
                    "I never meant to cause you sadness, $name. I apologize from my soul and hope we can heal together.$keyword",
                    "Please forgive my thoughtless words, $name. I value your peace of mind more than anything.$keyword"
                )
                else -> listOf( // Breakup Messages or fallback
                    "It breaks my heart that our paths are moving in different directions, $name. I will always cherish our memories.$keyword",
                    "We must accept that we can no longer find the happiness we had, $name. I wish you the very best.$keyword",
                    "After a lot of deep thinking, $name, I think it is healthier for both of us to move on.$keyword",
                    "Sometimes love means realizing we are growing apart, $name. Thank you for being a part of my journey.$keyword",
                    "$name, our beautiful chapter must come to a close. Take care of yourself always.$keyword"
                )
            }

            templates.forEach { text ->
                list.add(
                    GeneratedMessage(
                        text = text,
                        subcategory = if (category == "Pickup Lines") "Cute" else "Romantic",
                        mood = mood,
                        occasion = occasion
                    )
                )
            }
        }

        return list
    }
}
