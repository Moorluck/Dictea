package com.example.dictea.api.models

data class Result(
    val audio_links: List<AudioLink>,
    val featured: Boolean,
    val grammar_info: Any,
    val pos: String,
    val text: String,
    val translations: List<Translation>
)