package cossul.anderson.marvelcharacters.models

import java.io.Serializable

data class ComicsList (
    val items: ArrayList<ComicSummary>
): Serializable