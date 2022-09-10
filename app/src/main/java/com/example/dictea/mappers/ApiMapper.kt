package com.example.dictea.mappers

import com.example.dictea.api.models.Result
import com.example.dictea.models.Word

class ApiMapper {
    companion object {
        fun toWord(result: List<Result>) : Word? {
            if (result[0].audio_links == null) return null
            return Word(result[0].text, result[0].audio_links[0].url)
        }
    }
}