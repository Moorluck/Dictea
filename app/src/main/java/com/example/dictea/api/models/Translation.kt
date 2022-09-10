package com.example.dictea.api.models

data class Translation(
    val audio_links: List<AudioLink>?,
    val examples: List<Any>,
    val featured: Boolean,
    val pos: String,
    val text: String,
    val usage_frequency: Any
)