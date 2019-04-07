package cossul.anderson.marvelcharacters.models

import java.io.Serializable

data class Character (
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Image,
    val landscapeImage: Image,
    val comicsList: ComicsList = ComicsList(ArrayList())
): Serializable