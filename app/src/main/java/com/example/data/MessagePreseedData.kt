package com.example.data

object MessagePreseedData {
    val initialMessages: List<MessageEntity> by lazy {
        val list = ArrayList<MessageEntity>(23000)

        // --- 1. Pickup Lines: 10,000 (5,000 English, 5,000 Bengali) ---
        val enPickupStarts = listOf(
            "Are you ", "Do you have ", "Is your name ", "Are we at ", "I must be ",
            "Did it hurt when you ", "Excuse me, but ", "Is it hot in here, or ", "If beauty were time, ", "I am not a photographer, but ",
            "Do you believe in ", "Is your heart ", "If you were ", "My doctor says I need ", "Are you made of ",
            "Can I follow you home, because ", "Is there a spark here, or ", "If I could rearrange the alphabet, ", "Are you a keyboard, because ", "Do you have a band-aid? Because "
        )
        val enPickupMids = listOf(
            "a magician? Because ", "a star? Since ", "a thief? Because ", "the sun? Because ", "a map? Since ",
            "a campfire? Because ", "a bank loan? Because ", "a camera? Since ", "a wifi signal? Because ", "a gravity pull? Because ",
            "a dictionary? Because ", "a sweet dream? Since ", "a masterpiece? Because ", "a cup of tea? Since ", "a rose in bloom? Because ",
            "a treasure map? Since ", "a guiding light? Because ", "a lock to my key? Since ", "an angel? Because ", "the summer breeze? Since "
        )
        val enPickupEnds = listOf(
            "whenever I look at you, everyone else disappears. 😍", "I keep getting lost in your eyes. 💖", "you've got 'fine' written all over you. 😘", "you're CUtE. 💕", "you are absolutely breathtaking. 🌹",
            "you warm up my entire soul. 🥰", "you've got my interest completely. 💘", "I feel an instant connection. ✨", "you give meaning to my words. 📝", "you brighten up my darkest days. ☀️",
            "my world spins around you. 🌎", "you make my heart skip a beat. 💓", "I can't imagine my life without you. 💞", "you have the sweetest smile ever. 😊", "you stole my heart in a second. 🕵️",
            "you fit perfectly in my arms. 👩‍❤️‍👨", "you belong in a museum. 🎨", "I am drawn directly to you. 🧲", "you write the story of my life. 📖", "you sparkle more than anything. 💎",
            "you are worth more than gold. 🪙", "you sweep me off my feet. 🌬️", "you paint my life with beautiful colors. 🎨", "you bring hope to my world. 🌈", "you are my favorite comfort. ☕"
        )

        val bnPickupStarts = listOf(
            "আপনি কি ", "আপনার কাছে কি ", "আপনার নাম কি ", "আমরা কি কোনো ", "আমি মনে হয় ",
            "আকাশ থেকে পড়ার সময় কি ", "মাফ করবেন, কিন্তু ", "এখানে কি গরম বাড়ছে, নাকি ", "যদি সৌন্দর্য সময় হতো, তবে ", "আমি চিত্রশিল্পী নই, তবে ",
            "আপনি কি প্রথম দেখায় ", "আপনার কি মনে হয় ", "আপনি যদি কোনো ", "ডাক্তার আমাকে বলেছেন ", "আপনি কি কোনো ",
            "আমি কি আপনার পিছু নিতে পারি, কারণ ", "এখানে কি কোনো আকর্ষণ আছে, নাকি ", "আমি যদি বর্ণমালা নতুন করে সাজাতে পারতাম, ", "আপনি কি কোনো কিবোর্ড, কারণ ", "আপনার কাছে কি ব্যান্ড-এইড আছে? কারণ "
        )
        val bnPickupMids = listOf(
            "জাদুকর? কারণ ", "আকাশের তারা? যেহেতু ", "মন চোর? কারণ ", "ভোরের সূর্য? কারণ ", "নকশা বা ম্যাপ? যেহেতু ",
            "শীতের আগুন? কারণ ", "ব্যাংক লোন? কারণ ", "ক্যামেরা? যেহেতু ", "ওয়াইফাই সিগন্যال? কারণ ", "মাধ্যাকর্ষণ টান? কারণ ",
            "অভিধান? কারণ ", "মিষ্টি স্বপ্ন? যেহেতু ", "সেরা শিল্পকর্ম? কারণ ", "এক কাপ চা? যেহেতু ", "বাগানভরা গোলাপ? কারণ ",
            "গুপ্তধনের সন্ধান? যেহেতু ", "পথপ্রদর্শক আলো? কারণ ", "আমার চাবির তালা? যেহেতু ", "স্বর্গের পরি? কারণ ", "বসন্তের হাওয়া? যেহেতু "
        )
        val bnPickupEnds = listOf(
            "আপনার দিকে তাকালে চারপাশের সবাই উধাও হয়ে যায়। 😍", "আমি আপনার চোখের মায়ায় হারিয়ে গেছি। 💖", "আপনার হাসিতেই যেন সব সুখ লুকিয়ে আছে। 😘", "আপনি দেখতে ভীষণ মিষ্টি। 💕", "আপনার মায়াবী রূপ সত্যিই অসাধারণ। 🌹",
            "আমার পুরো হৃদয় আপনি জয় করে নিয়েছেন। 🥰", "আপনার কথা ছাড়া আমার দিন কাটে না। 💘", "আমি আপনার সাথে একটা সুন্দর বিকেল কাটাতে চাই। ✨", "আপনি আমার জীবনের আসল অর্থ। 📝", "আমার মেঘলা আকাশ আপনার আলোয় রোদ হাসে। ☀️",
            "আমার পৃথিবীটা আপনার চারপাশেই ঘোরে। 🌎", "আপনাকে দেখলেই আমার হৃদস্পন্দন বেড়ে যায়। 💓", "আপনাকে ছাড়া আমি নিজেকে ভাবতে পারি না। 💞", "আপনার কাছে পৃথিবীর সবচেয়ে মিষ্টি হাসি আছে। 😊", "এক মুহূর্তেই আপনি আমার মন কেড়ে নিলেন। 🕵️",
            "আপনার বুকেই আমি শান্তি খুঁজে পাই। 👩‍❤️‍👨", "আপনার সৌন্দর্য ফ্রেমবন্দী করে রাখার মতো। 🎨", "আমি যেন আপনার দিকেই আকর্ষিত হচ্ছি। 🧲", "আপনি আমার গল্পের সবচেয়ে সুন্দর চরিত্র। 📖", "হীরার চেয়েও আপনার চোখ বেশি চকচক করে। 💎",
            "কোটি টাকার চেয়েও আপনার সঙ্গ বেশি দামী। 🪙", "আপনার মিষ্টি হাওয়া আমাকে ভাসিয়ে নিয়ে যায়। 🌬️", "আপনি আমার জীবনে হাজার রঙের ছোঁয়া এনেছেন। 🎨", "আপনিই আমার আশার রঙিন রামধনু। 🌈", "আমার অলস সময়ের আপনিই একমাত্র শান্তি। ☕"
        )

        // Generate 5,000 English Pickup Lines
        for (i in 0 until 5000) {
            val start = enPickupStarts[i % enPickupStarts.size]
            val mid = enPickupMids[(i / enPickupStarts.size) % enPickupMids.size]
            val end = enPickupEnds[(i / (enPickupStarts.size * enPickupMids.size)) % enPickupEnds.size]
            val mood = when (i % 5) {
                0 -> "Romantic"
                1 -> "Funny"
                2 -> "Cute"
                3 -> "Sweet"
                else -> "Cheesy"
            }
            val sub = when (i % 6) {
                0 -> "Funny"
                1 -> "Cute"
                2 -> "Romantic"
                3 -> "Sweet"
                4 -> "Cheesy"
                else -> "Nerdy"
            }
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Pickup Lines",
                    subcategory = sub,
                    language = "English",
                    mood = mood,
                    occasion = if (i % 4 == 0) "First Date" else "None"
                )
            )
        }

        // Generate 5,000 Bengali Pickup Lines
        for (i in 0 until 5000) {
            val start = bnPickupStarts[i % bnPickupStarts.size]
            val mid = bnPickupMids[(i / bnPickupStarts.size) % bnPickupMids.size]
            val end = bnPickupEnds[(i / (bnPickupStarts.size * bnPickupMids.size)) % bnPickupEnds.size]
            val mood = when (i % 5) {
                0 -> "Romantic"
                1 -> "Funny"
                2 -> "Cute"
                3 -> "Sweet"
                else -> "Cheesy"
            }
            val sub = when (i % 6) {
                0 -> "Funny"
                1 -> "Cute"
                2 -> "Romantic"
                3 -> "Sweet"
                4 -> "Cheesy"
                else -> "Coffee"
            }
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Pickup Lines",
                    subcategory = sub,
                    language = "বাংলা",
                    mood = mood,
                    occasion = if (i % 4 == 0) "First Date" else "None"
                )
            )
        }

        // --- 2. Love Messages: 5,000 (2,500 English, 2,500 Bengali) ---
        val enLoveStarts = listOf(
            "My dearest, ", "Just wanted to say, ", "From the bottom of my heart, ", "Every single day, ", "Since the day we met, ",
            "When I think of you, ", "You are my everything, and ", "No matter what happens, ", "Walking through this life with you, ", "With you by my side, "
        )
        val enLoveMids = listOf(
            "my love for you grows stronger because ", "you are the light of my life since ", "I am forever grateful because ", "my heart beats only for you as ", "you make every single moment special because ",
            "you have taught me what true love is since ", "I find peace in your warm embrace as ", "you inspire me to be a better person because ", "I fall in love with you all over again since ", "my thoughts are always filled with you as "
        )
        val enLoveEnds = listOf(
            "you are the most beautiful chapter of my life. 💖", "your love is the greatest gift I have ever received. 🎁", "I want to spend every tomorrow loving you. 🌹", "you make my ordinary life extraordinary. ✨", "without you, I would be completely lost. 🥰",
            "you are my anchor in every storm. ⚓", "your happiness is my highest priority. 💞", "I cherish every second we share together. ⏱️", "you are my dream come true. 🌸", "my heart belongs to you forever and always. 💘",
            "you are my sunshine on a rainy day. ☀️", "your voice is my favorite sweet melody. 🎵", "I promise to love you through all seasons. 🍂", "you complete my puzzle perfectly. 🧩", "I love you more than words can express. 💬",
            "you are my favorite thought. 💭", "being with you feels like home. 🏡", "you make my heart smile. 😊", "my soul is bound to yours. 🔗", "you are my forever and ever. ♾️",
            "your touch warms my cold days. 🔥", "I will stand by you through thick and thin. 🤝", "you are my happy place. 🏝️", "I miss you more with every passing second. 🥺", "you are the melody to my heart's song. 🎻"
        )

        val bnLoveStarts = listOf(
            "আমার প্রিয়তম, ", "শুধু তোমাকে বলতে চাই, ", "আমার হৃদয়ের গভীর থেকে, ", "প্রতিটি দিন যখন কাটে, ", "যেদিন থেকে তোমার সাথে দেখা, ",
            "যখনই আমি তোমার কথা ভাবি, ", "তুমিই আমার সব কিছু, এবং ", "পরিস্থিতি যাই হোক না কেন, ", "তোমার সাথে পথ চলার অনুভূতি, ", "তুমি যখন পাশে থাকো, "
        )
        val bnLoveMids = listOf(
            "তোমার প্রতি আমার ভালোবাসা আরও গভীর হয় কারণ ", "তুমিই আমার জীবনের একমাত্র আলো যেহেতু ", "আমি আল্লাহর কাছে চিরকৃতজ্ঞ কারণ ", "আমার হৃদয় শুধু তোমার জন্যই স্পন্দিত হয় যেহেতু ", "তুমি প্রতিটি মুহূর্তকে অসাধারণ করে তোলো কারণ ",
            "তুমি আমাকে ভালোবাসার আসল মানে শিখিয়েছ যেহেতু ", "তোমার উষ্ণ জড়িয়ে ধরার মাঝেই আমি শান্তি পাই যেহেতু ", "তুমি আমাকে আরও ভালো মানুষ হতে অনুপ্রাণিত করো কারণ ", "আমি বারবার তোমার প্রেমে নতুন করে পড়ি যেহেতু ", "আমার সারাদিনের সব চিন্তা শুধু তোমাকে ঘিরেই থাকে কারণ "
        )
        val bnLoveEnds = listOf(
            "তুমি আমার জীবনের সবচেয়ে সুন্দর অধ্যায়। 💖", "তোমার ভালোবাসা আমার পাওয়া সেরা উপহার। 🎁", "আমি আমার প্রতিটি আগামী দিন তোমাকে ভালোবাসতে চাই। 🌹", "তুমি আমার সাধারণ জীবনকে অসাধারণ করে তুলেছ। ✨", "তোমাকে ছাড়া আমি আসলেই পথহারা। 🥰",
            "তুমি আমার জীবনের কঠিন সময়ের ভরসা। ⚓", "তোমার মুখের হাসিটাই আমার একমাত্র চাওয়া। 💞", "আমাদের প্রতিটি মুহূর্ত আমি আজীবন মনে রাখবো। ⏱️", "তুমি আমার পূরণ হওয়া সেরা স্বপ্ন। 🌸", "আমার এই মন চিরকালের জন্য তোমার হয়ে গেছে। 💘",
            "তুমি মেঘলা দিনে আমার এক চিমটি রোদ। ☀️", "তোমার কণ্ঠ আমার প্রিয়তম সুর। 🎵", "সব ঋতুতেই আমি তোমার পাশে থাকার অঙ্গীকার করছি। 🍂", "তুমি আমার অপূর্ণ জীবনকে পূর্ণতা দিয়েছ। 🧩", "তোমাকে কতটা ভালোবাসি তা ভাষায় বলা অসম্ভব। 💬",
            "তুমি আমার দিনের সেরা ভাবনা। 💭", "তোমার পাশে থাকলে নিজেকে নিরাপদ মনে হয়। 🏡", "তুমি আমার মনে খুশির দোলা দাও। 😊", "আমার আত্মা তোমার আত্মার সাথে বাধা। 🔗", "তুমিই আমার অনন্তকালের সঙ্গী। ♾️",
            "তোমার ছোঁয়া আমার বিষণ্ণতা দূর করে দেয়। 🔥", "সুখে-দুঃখে আমি আজীবন তোমার হাত ধরে থাকবো। 🤝", "তুমিই আমার মনের সুখের ঠিকানা। 🏝️", "প্রতিটি মুহূর্ত তোমাকে আগের চেয়ে বেশি মিস করি। 🥺", "তুমি আমার হৃদয়ের স্পন্দন। 🎻"
        )

        // Generate 2,500 English Love Messages
        for (i in 0 until 2500) {
            val start = enLoveStarts[i % enLoveStarts.size]
            val mid = enLoveMids[(i / enLoveStarts.size) % enLoveMids.size]
            val end = enLoveEnds[(i / (enLoveStarts.size * enLoveMids.size)) % enLoveEnds.size]
            val mood = when (i % 4) {
                0 -> "Romantic"
                1 -> "Happy"
                2 -> "Shy"
                else -> "Confident"
            }
            val sub = when (i % 6) {
                0 -> "Good Morning"
                1 -> "Good Night"
                2 -> "Missing You"
                3 -> "Thinking of You"
                4 -> "I Love You"
                else -> "Appreciation"
            }
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Love Messages",
                    subcategory = sub,
                    language = "English",
                    mood = mood,
                    occasion = if (i % 5 == 0) "Anniversary" else "None"
                )
            )
        }

        // Generate 2,500 Bengali Love Messages
        for (i in 0 until 2500) {
            val start = bnLoveStarts[i % bnLoveStarts.size]
            val mid = bnLoveMids[(i / bnLoveStarts.size) % bnLoveMids.size]
            val end = bnLoveEnds[(i / (bnLoveStarts.size * bnLoveMids.size)) % bnLoveEnds.size]
            val mood = when (i % 4) {
                0 -> "Romantic"
                1 -> "Happy"
                2 -> "Shy"
                else -> "Confident"
            }
            val sub = when (i % 6) {
                0 -> "Good Morning"
                1 -> "Good Night"
                2 -> "Missing You"
                3 -> "Thinking of You"
                4 -> "I Love You"
                else -> "Appreciation"
            }
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Love Messages",
                    subcategory = sub,
                    language = "বাংলা",
                    mood = mood,
                    occasion = if (i % 5 == 0) "Anniversary" else "None"
                )
            )
        }

        // --- 3. Romantic Quotes: 3,000 (1,500 English, 1,500 Bengali) ---
        val enQuoteStarts = listOf(
            "An ancient poet once wrote: ", "The stars whisper a timeless truth: ", "True lovers understand that ", "In the book of destiny, ", "A wise heart once whispered: ",
            "Across the endless galaxies of time, ", "The finest poetry cannot describe how ", "Legends say that two souls unite because ", "In the silence of the night, we learn that ", "Under the silver moonlight, it is clear that "
        )
        val enQuoteMids = listOf(
            "love is not about finding perfection, but ", "two hearts beating as one can conquer ", "to love and be loved is ", "your soul and my soul are ", "the sweet music of your voice is ",
            "when we hold each other's hands, ", "our connection was written in the stars, where ", "true romance is an quiet flame that ", "every look you give me is a masterpiece because ", "there is no measure of distance when ",
            "a single moment of pure affection can ", "your touch possesses the magical power to ", "loving you is as natural as breathing, and ", "our journey is an endless sky where ", "to find someone who knows your silent thoughts is "
        )
        val enQuoteEnds = listOf(
            "to feel the warmth of heaven itself. ✨", "any obstacle in the universe. 🌎", "the ultimate purpose of our existence. 💖", "destined to walk together through eternity. ♾️", "the only song my heart desires. 🎶",
            "time stops and nothing else matters. ⏱️", "the heavens celebrate our bond. 🌌", "burns brighter than the sun. 🔥", "it captures the beauty of your soul. 🎨", "two souls reside in the same space. 🏡"
        )

        val bnQuoteStarts = listOf(
            "কোনো এক কবি লিখেছিলেন: ", "আকাশের তারাগুলো এক চিরন্তন সত্য জানায়: ", "হৃদয় দিয়ে অনুভব করলে জানা যায় যে, ", "ভাগ্যের পাতায় ঈশ্বরের নিজের হাতে লেখা: ", "এক জ্ঞানী মন মৃদুস্বরে বলেছিল: ",
            "অনন্ত সময়ের এই মহাবিশ্বে, ", "সবচেয়ে সুন্দর কবিতাটিও প্রকাশ করতে পারে না যে ", "উপকথা বলে যে দুটি আত্মা একত্রিত হয় কারণ ", "রাতের নিস্তব্ধতায় আমরা শিখি যে, ", "চাঁদের জোছনা আলোয় পরিষ্কার বোঝা যায় যে, "
        )
        val bnQuoteMids = listOf(
            "ভালোবাসা নিখুঁত মানুষ খোঁজা নয়, বরং ", "দুটি হৃদয়ের একই সাথে স্পন্দিত হওয়া জয় করতে পারে ", "কাউকে গভীরভাবে ভালোবাসা এবং ভালোবাসাপ্রাপ্ত হওয়া হলো ", "তোমার আত্মা এবং আমার আত্মা হলো ", "তোমার কণ্ঠের মিষ্টি সুর হলো ",
            "যখন আমরা একে অপরের হাত ধরে থাকি, ", "আমাদের এই বন্ধন আকাশের তারায় লেখা ছিল, যেখানে ", " his ", "তোমার প্রতিটি চাহনি এক একটি অনবদ্য শিল্পকর্ম কারণ ", "দূরত্ব কখনোই বাধা হয়ে দাঁড়াতে পারে না যখন ",
            "এক মুহূর্তের খাঁটি মায়া দূর করতে পারে ", "তোমার পরশ জাদুকরী শক্তিতে পূর্ণ যা ", "তোমাকে ভালোবাসা শ্বাস নেওয়ার মতোই স্বাভাবিক, এবং ", "আমাদের পথ চলা এক অন্তহীন আকাশ যেখানে ", "এমন কাউকে পাওয়া যে তোমার নীরবতা বুঝতে পারে তা হলো "
        )
        val bnQuoteEnds = listOf(
            "স্বর্গের অনুভূতি পৃথিবীতেই খুঁজে পাওয়া। ✨", "মহাবিশ্বের যেকোনো কঠিনতম বাধা। 🌎", "আমাদের বেঁচে থাকার চরম সার্থকতা। 💖", "অনন্তকাল ধরে একসাথে চলার জন্য নির্ধারিত। ♾️", "একমাত্র গান যা আমার হৃদয় শুনতে চায়। 🎶",
            "সময় থমকে যায় এবং অন্য কিছুর অস্তিত্ব থাকে না। ⏱️", "স্বয়ং আকাশ আমাদের এই প্রেম উদযাপন করে। 🌌", "সূর্যের চেয়েও বেশি উজ্জ্বলভাবে জ্বলে। 🔥", "এটি তোমার হৃদয়ের গভীর সৌন্দর্য প্রকাশ করে। 🎨", "দুটি মন একই স্পন্দনে স্পন্দিত হয়। 🏡"
        )

        // Generate 1,500 English Romantic Quotes
        for (i in 0 until 1500) {
            val start = enQuoteStarts[i % enQuoteStarts.size]
            val mid = enQuoteMids[(i / enQuoteStarts.size) % enQuoteMids.size]
            val end = enQuoteEnds[(i / (enQuoteStarts.size * enQuoteMids.size)) % enQuoteEnds.size]
            val sub = when (i % 4) {
                0 -> "Sweet Quotes"
                1 -> "Deep Love"
                2 -> "Eternal Romance"
                else -> "Poetic Quotes"
            }
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Romantic Quotes",
                    subcategory = sub,
                    language = "English",
                    mood = "Romantic",
                    occasion = "None"
                )
            )
        }

        // Generate 1,500 Bengali Romantic Quotes
        for (i in 0 until 1500) {
            val start = bnQuoteStarts[i % bnQuoteStarts.size]
            val mid = bnQuoteMids[(i / bnQuoteStarts.size) % bnQuoteMids.size]
            val end = bnQuoteEnds[(i / (bnQuoteStarts.size * bnQuoteMids.size)) % bnQuoteEnds.size]
            val sub = when (i % 4) {
                0 -> "Sweet Quotes"
                1 -> "Deep Love"
                2 -> "Eternal Romance"
                else -> "Poetic Quotes"
            }
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Romantic Quotes",
                    subcategory = sub,
                    language = "বাংলা",
                    mood = "Romantic",
                    occasion = "None"
                )
            )
        }

        // --- 4. Conversation Starters: 2,000 (1,000 English, 1,000 Bengali) ---
        val enStartersStarts = listOf(
            "If you could ", "What is your absolute favorite ", "Tell me about a ", "If we were to ", "Would you rather ",
            "What's the most interesting ", "If you had a secret ", "What is one thing that ", "What is your idea of ", "If you could change one "
        )
        val enStartersMids = listOf(
            "travel to any place in the world, where ", "spend a whole day doing anything, what ", "describe your perfect life, how ", "have any superpower for 24 hours, what ", "recommend just one book or movie, which ",
            "share your funniest childhood memory, how ", "eat only one food for the rest of your life, what ", "design a dream home anywhere, where ", "have a deep conversation with anyone, who ", "choose a soundtrack for your daily life, what "
        )
        val enStartersEnds = listOf(
            "would you choose first? Let's chat! 😊", "would make you smile the most? Tell me! 🌟", "would you share with me? 💬", "is the story behind it? I'd love to know. 📖", "would be your top secret? 🤫",
            "brings back the sweetest feelings? 🎈", "would you explore first? 🗺️", "would be the perfect start? ☕", "would you love to talk about? 💭", "makes you feel alive and happy? 🌈"
        )

        val bnStartersStarts = listOf(
            "আপনি যদি কখনো ", "আপনার সবচেয়ে পছন্দের ", "আমাকে এমন একটি ", "আমরা যদি কখনো ", "আপনি কি বরং ",
            "আপনার জীবনের সবচেয়ে মজার ", "আপনার কাছে যদি একটি গোপন ", "এমন একটি জিনিস যা আপনাকে ", "আপনার মতে একদম নিখুঁত ", "আপনি যদি নিজের একটি "
        )
        val bnStartersMids = listOf(
            "বিশ্বের যেকোনো জায়গায় ভ্রমণ করতে পারতেন, তবে ", "সারাদিন ধরে নিজের ইচ্ছেমতো যেকোনো কাজ করতে পারতেন, তবে ", "নিজের নিখুঁত জীবনের বর্ণনা দিতে পারতেন, তবে ", "২৪ ঘণ্টার জন্য কোনো অলৌকিক ক্ষমতা পেতেন, তবে ", "আমাকে একটি মাত্র বই বা সিনেমা সাজেস্ট করতে পারতেন, তবে ",
            "ছোটবেলার কোনো মজার স্মৃতি শেয়ার করতে পারতেন, তবে ", "সারা জীবনের জন্য একটি মাত্র খাবার বেছে নিতে হতো, তবে ", "যেকোনো জায়গায় স্বপ্নের বাড়ি বানাতে পারতেন, তবে ", "যেকোনো ব্যক্তির সাথে গভীর আড্ডা দিতে পারতেন, তবে ", "নিজের জীবনের জন্য একটি থিম সং বা সুর বেছে নিতে পারতেন, তবে "
        )
        val bnStartersEnds = listOf(
            "কোন জায়গাটি আগে বেছে নিতেন? চলুন আড্ডা দেই! 😊", "আপনাকে সবচেয়ে বেশি হাসাতো? বলুন না! 🌟", "আমার সাথে শেয়ার করতে চাইবেন? 💬", "তার পেছনের গল্পটি কী? আমি শুনতে চাই। 📖", "সেটি কী হতো বলুন তো? 🤫",
            "সবচেয়ে মিষ্টি অনুভূতি ফিরিয়ে আনে? 🎈", "কোনটি আগে অন্বেষণ করতেন? 🗺️", "সবচেয়ে সুন্দর শুরু হতো? এক কাপ চা খাওয়া যাক? ☕", "কোন বিষয়টি নিয়ে কথা বলতে ভালোবাসেন? 💭", "আপনাকে সবচেয়ে প্রাণবন্ত ও সুখী করে তোলে? 🌈"
        )

        // Generate 1,000 English Conversation Starters
        for (i in 0 until 1000) {
            val start = enStartersStarts[i % enStartersStarts.size]
            val mid = enStartersMids[(i / enStartersStarts.size) % enStartersMids.size]
            val end = enStartersEnds[(i / (enStartersStarts.size * enStartersMids.size)) % enStartersEnds.size]
            val sub = when (i % 4) {
                0 -> "First Message"
                1 -> "First Date"
                2 -> "Online Dating"
                else -> "Ice Breakers"
            }
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Conversation Starters",
                    subcategory = sub,
                    language = "English",
                    mood = "Funny",
                    occasion = "None"
                )
            )
        }

        // Generate 1,000 Bengali Conversation Starters
        for (i in 0 until 1000) {
            val start = bnStartersStarts[i % bnStartersStarts.size]
            val mid = bnStartersMids[(i / bnStartersStarts.size) % bnStartersMids.size]
            val end = bnStartersEnds[(i / (bnStartersStarts.size * bnStartersMids.size)) % bnStartersEnds.size]
            val sub = when (i % 4) {
                0 -> "First Message"
                1 -> "First Date"
                2 -> "Online Dating"
                else -> "Ice Breakers"
            }
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Conversation Starters",
                    subcategory = sub,
                    language = "বাংলা",
                    mood = "Funny",
                    occasion = "None"
                )
            )
        }

        // --- 5. Compliments: 1,000 (500 English, 500 Bengali) ---
        val enCompStarts = listOf("You have ", "I absolutely love ", "You make the world ", "There is something about ", "You are truly ")
        val enCompMids = listOf(
            "the most captivating smile that ", "an incredible energy that ", "a beautiful and kind heart which ", "the sweetest eyes that ", "a unique way of thinking that ",
            "a lovely sense of humor which ", "such an inspiring personality that ", "a gentle, caring touch that ", "a rare and sparkling intelligence which ", "a calming, peaceful aura that "
        )
        val enCompEnds = listOf(
            "brightens up my entire day. ☀️", "makes everyone around you feel valued. 🌟", "always leaves me speechless. 💖", "brings so much warmth to my life. 🔥", "is impossible to forget. 🌹",
            "inspires me to be a better person. 🥰", "makes my world a better place. 🌎", "fills my heart with pure joy. 🌸", "is like a breath of fresh air. 🍃", "makes you shine brighter than any star. 🌌"
        )

        val bnCompStarts = listOf("আপনার মাঝে আছে ", "আমি সত্যিই ভীষণ পছন্দ করি ", "আপনি এই পৃথিবীকে ", "আপনার একটি বিশেষ জিনিস ", "আপনি সত্যিই এক ")
        val bnCompMids = listOf(
            "সবচেয়ে আকর্ষণীয় এক মিষ্টি হাসি যা ", "অসাধারণ এক ইতিবাচক শক্তি যা ", "ভীষণ সুন্দর ও দয়ালু একটি হৃদয় যা ", "অপূর্ব মায়াবী দুটি চোখ যা ", "অনন্য এক চিন্তাভাবনা যা ",
            "সুন্দর এক রসবোধ যা ", "অনুপ্রেরণাদায়ক এক ব্যক্তিত্ব যা ", "ভীষণ যত্নশীল এক মনোভাব যা ", "বিরল ও তীক্ষ্ণ এক বুদ্ধিমত্তা যা ", "শান্ত ও স্নিগ্ধ এক পরিবেশ যা "
        )
        val bnCompEnds = listOf(
            "আমার পুরো দিনটিকে উজ্জ্বল করে তোলে। ☀️", "আশেপাশের সবাইকে সম্মানিত বোধ করায়। 🌟", "আমাকে সবসময় মুগ্ধ ও নির্বাক করে দেয়। 💖", "আমার জীবনে অনেক গভীর ভালোবাসা এনে দেয়। 🔥", "কখনোই ভুলে যাওয়া সম্ভব নয়। 🌹",
            "আমাকে একজন ভালো মানুষ হতে অনুপ্রাণিত করে। 🥰", "আমার পৃথিবীকে অনেক সুন্দর করে তোলে। 🌎", "আমার হৃদয়কে বিশুদ্ধ আনন্দে ভরিয়ে দেয়। 🌸", "বয়ে আনে এক ঝলক তাজা ঠান্ডা বাতাস। 🍃", "আকাশের যেকোনো তারার চেয়ে আপনাকে বেশি উজ্জ্বল দেখায়। 🌌"
        )

        // Generate 500 English Compliments
        for (i in 0 until 500) {
            val start = enCompStarts[i % enCompStarts.size]
            val mid = enCompMids[(i / enCompStarts.size) % enCompMids.size]
            val end = enCompEnds[(i / (enCompStarts.size * enCompMids.size)) % enCompEnds.size]
            val sub = when (i % 4) {
                0 -> "Smile"
                1 -> "Eyes"
                2 -> "Personality"
                else -> "Kindness"
            }
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Compliments",
                    subcategory = sub,
                    language = "English",
                    mood = "Happy",
                    occasion = "None"
                )
            )
        }

        // Generate 500 Bengali Compliments
        for (i in 0 until 500) {
            val start = bnCompStarts[i % bnCompStarts.size]
            val mid = bnCompMids[(i / bnCompStarts.size) % bnCompMids.size]
            val end = bnCompEnds[(i / (bnCompStarts.size * bnCompMids.size)) % bnCompEnds.size]
            val sub = when (i % 4) {
                0 -> "Smile"
                1 -> "Eyes"
                2 -> "Personality"
                else -> "Kindness"
            }
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Compliments",
                    subcategory = sub,
                    language = "বাংলা",
                    mood = "Happy",
                    occasion = "None"
                )
            )
        }

        // --- 6. Apology Messages: 1,000 (500 English, 500 Bengali) ---
        val enApolStarts = listOf("I am deeply sorry ", "Please forgive me ", "My heart is heavy because ", "I never meant ", "I apologize from my soul ")
        val enApolMids = listOf(
            "for hurting your beautiful feelings when ", "for my thoughtless and foolish words because ", "for creating this unnecessary distance as ", "for making you sad, since ", "for the misunderstanding that occurred, because ",
            "for letting my anger take control when ", "for not being there for you when ", "for causing tears to fall from your eyes as ", "for my actions which were wrong, because ", "for failing to understand your thoughts as "
        )
        val enApolEnds = listOf(
            "your happiness is my highest priority. 💖", "I promise to learn and do better. 🤝", "you mean the absolute world to me. 🌎", "I can't bear to see you sad or angry. 🥺", "our beautiful relationship is worth saving. 🌹",
            "I want to put a smile back on your face. 😊", "your peace of mind is what I care about. 🕊️", "I miss you more than anything. 💕", "I will do whatever it takes to repair this. 💞", "I love you with all my heart and soul. 💘"
        )

        val bnApolStarts = listOf("আমি সত্যিই অন্তর থেকে ক্ষমাপ্রার্থী ", "দয়া করে আমাকে ক্ষমা করে দিন ", "আমার মনটা ভীষণ ভারাক্রান্ত কারণ ", "আমি কখনো আপনার মনে আঘাত দিতে চাইনি ", "আমি আমার পুরো মন থেকে দুঃখ প্রকাশ করছি ")
        val bnApolMids = listOf(
            "আপনার সুন্দর অনুভূতিগুলোকে আঘাত করার জন্য যখন ", "আমার না ভেবে বলা কিছু বোকা কথার জন্য কারণ ", "আমাদের মাঝে এই অপ্রয়োজনীয় দূরত্ব সৃষ্টির জন্য যেহেতু ", "আপনাকে কষ্ট দেওয়ার জন্য, কারণ ", "যে ভুল বোঝাবুঝির সৃষ্টি হয়েছে তার জন্য, যেহেতু ",
            "আমার রাগ নিয়ন্ত্রণ করতে না পারার জন্য যখন ", "আপনার প্রয়োজনে পাশে থাকতে না পারার জন্য যখন ", "আপনার চোখে পানি আনার জন্য, কারণ ", "আমার অন্যায় আচরণ ও কাজের জন্য, যেহেতু ", "আপনার নীরব চোখের ভাষা বুঝতে না পারার জন্য যেহেতু "
        )
        val bnApolEnds = listOf(
            "আপনার হাসিটাই আমার কাছে সবচেয়ে গুরুত্বপূর্ণ। 💖", "আমি নিজেকে শুধরে নেওয়ার অঙ্গীকার করছি। 🤝", "আপনি আমার জীবনের পুরো পৃথিবী জুড়ে আছেন। 🌎", "আমি আপনাকে একটুও কষ্ট পেতে দেখতে পারি না। 🥺", "আমাদের সুন্দর সম্পর্কটি যেকোনো কিছুর চেয়ে মূল্যবান। 🌹",
            "আমি আবারো আপনার মুখে সেই মিষ্টি হাসি দেখতে চাই। 😊", "আপনার মনের শান্তিই আমার একমাত্র চাওয়া। 🕊️", "আমি আপনাকে প্রতিটি ক্ষণ ভীষণ মিস করছি। 💕", "আমাদের সম্পর্ক ঠিক করতে আমি সবকিছু করতে রাজি। 💞", "আমি আপনাকে আমার হৃদয়ের সবটুকু দিয়ে ভালোবাসি। 💘"
        )

        // Generate 500 English Apologies
        for (i in 0 until 500) {
            val start = enApolStarts[i % enApolStarts.size]
            val mid = enApolMids[(i / enApolStarts.size) % enApolMids.size]
            val end = enApolEnds[(i / (enApolStarts.size * enApolMids.size)) % enApolEnds.size]
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Apology Messages",
                    subcategory = "Sorry Messages",
                    language = "English",
                    mood = "Shy",
                    occasion = "None"
                )
            )
        }

        // Generate 500 Bengali Apologies
        for (i in 0 until 500) {
            val start = bnApolStarts[i % bnApolStarts.size]
            val mid = bnApolMids[(i / bnApolStarts.size) % bnApolMids.size]
            val end = bnApolEnds[(i / (bnApolStarts.size * bnApolMids.size)) % bnApolEnds.size]
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Apology Messages",
                    subcategory = "Sorry Messages",
                    language = "বাংলা",
                    mood = "Shy",
                    occasion = "None"
                )
            )
        }

        // --- 7. Breakup Messages: 1,000 (500 English, 500 Bengali) ---
        val enBreakStarts = listOf("It breaks my heart that ", "We must accept that ", "After a lot of deep thinking, ", "I think it is time for us ", "Sometimes love means realizing ")
        val enBreakMids = listOf(
            "our paths are moving in different directions, because ", "we can no longer find the happiness we had as ", "holding on is causing more pain than letting go, since ", "we are growing apart despite our best efforts as ", "our stories are meant to have separate endings, because ",
            "we deserve to find peace individually, since ", "the beautiful spark we had has faded away as ", "it is healthier for both of us to move on, because ", "we want different things in life now, since ", "our compatibility has reached its limit as "
        )
        val enBreakEnds = listOf(
            "I will always cherish the memories we shared. 🕊️", "I sincerely wish you the absolute best in life. 🌟", "may you find the true happiness you deserve. 💖", "it is time to say a quiet, peaceful goodbye. 🚪", "thank you for being a part of my journey. 🙏",
            "we must let each other heal now. 🩹", "our beautiful chapters must come to a close. 📖", "I will always respect the love we once had. 🌹", "may your future be bright and full of peace. ☀️", "goodbye, and take care of yourself. 👋"
        )

        val bnBreakStarts = listOf("আমার হৃদয় ভেঙে যায় এটা ভেবে যে ", "আমাদের মেনে নিতেই হবে যে ", "অনেক গভীর চিন্তাভাবনার পর, ", "আই থিঙ্ক আমাদের এখন সময় হয়েছে ", "মাঝে মাঝে ভালোবাসা মানে এটা অনুধাবন করা যে ")
        val bnBreakMids = listOf(
            "আমাদের চলার পথ দুটি ভিন্ন দিকে চলে গেছে, কারণ ", "আমরা আর আগের মতো সেই খুশি খুঁজে পাচ্ছি না যেহেতু ", "ধরে রাখার চেয়ে ছেড়ে দেওয়া অনেক কম বেদনাদায়ক, কারণ ", "চেষ্টা করার পরেও আমরা একে অপরের থেকে দূরে সরে যাচ্ছি যেহেতু ", "আমাদের জীবনের গল্পের সমাপ্তি ভিন্ন হতে চলেছে, কারণ ",
            "আমাদের নিজেদের ভালোর জন্যই আলাদা হওয়া প্রয়োজন, যেহেতু ", "আমাদের মাঝে যে সুন্দর সম্পর্ক ছিল তা হারিয়ে গেছে যেহেতু ", "এগিয়ে যাওয়াটাই আমাদের দুজনের জন্য বেশি স্বস্তির, কারণ ", "আমরা এখন জীবনে সম্পূর্ণ ভিন্ন জিনিস প্রত্যাশা করি, যেহেতু ", "আমাদের মানিয়ে নেওয়ার ক্ষমতা শেষ সীমায় পৌঁছেছে যেহেতু "
        )
        val bnBreakEnds = listOf(
            "আমি আমাদের শেয়ার করা সুন্দর স্মৃতিগুলো সারাজীবন মনে রাখবো। 🕊️", "আমি আপনার জীবনের প্রতিটি সাফল্য ও মঙ্গল কামনা করি। 🌟", "আশা করি আপনি নিজের কাঙ্ক্ষিত সুখ খুঁজে পাবেন। 💖", "এখন সময় শান্ত মনে চিরবিদায় জানানোর। 🚪", "আমার জীবনের যাত্রাপথে অংশ নেওয়ার জন্য ধন্যবাদ। 🙏",
            "আমাদের এখন নিজেদের ক্ষতগুলো নিরাময় করার সময় দিতে হবে। 🩹", "আমাদের সুন্দর অধ্যায়টির এখানেই সমাপ্তি টানা প্রয়োজন। 📖", "আমাদের আগের ভালোবাসার প্রতি আমার চিরকাল সম্মান থাকবে। 🌹", "আপনার ভবিষ্যৎ সুন্দর ও শান্তিময় হোক। ☀️", "ভালো থাকবেন, এবং নিজের যত্ন নেবেন। 👋"
        )

        // Generate 500 English Breakup Messages
        for (i in 0 until 500) {
            val start = enBreakStarts[i % enBreakStarts.size]
            val mid = enBreakMids[(i / enBreakStarts.size) % enBreakMids.size]
            val end = enBreakEnds[(i / (enBreakStarts.size * enBreakMids.size)) % enBreakEnds.size]
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Breakup Messages",
                    subcategory = "Heartbroken",
                    language = "English",
                    mood = "Shy",
                    occasion = "None"
                )
            )
        }

        // Generate 500 Bengali Breakup Messages
        for (i in 0 until 500) {
            val start = bnBreakStarts[i % bnBreakStarts.size]
            val mid = bnBreakMids[(i / bnBreakStarts.size) % bnBreakMids.size]
            val end = bnBreakEnds[(i / (bnBreakStarts.size * bnBreakMids.size)) % bnBreakEnds.size]
            list.add(
                MessageEntity(
                    text = "$start$mid$end",
                    category = "Breakup Messages",
                    subcategory = "Heartbroken",
                    language = "বাংলা",
                    mood = "Shy",
                    occasion = "None"
                )
            )
        }

        list
    }

    val defaultCollections = listOf(
        BookmarkCollectionEntity("Best for Crush", "#E91E63", "favorite"),
        BookmarkCollectionEntity("Funny & Cheesy", "#FF9800", "mood"),
        BookmarkCollectionEntity("Wedding Wishes", "#9C27B0", "celebration"),
        BookmarkCollectionEntity("Daily Gems", "#4CAF50", "star")
    )
}
