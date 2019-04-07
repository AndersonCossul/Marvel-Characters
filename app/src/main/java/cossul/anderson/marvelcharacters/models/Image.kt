package cossul.anderson.marvelcharacters.models

import java.io.Serializable

data class Image (
    val extension: String,
    val path: String
): Serializable