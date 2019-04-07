package cossul.anderson.marvelcharacters.models

import java.io.Serializable

data class ComicSummary (
    val title: String,
    val thumbnail: Image
): Serializable