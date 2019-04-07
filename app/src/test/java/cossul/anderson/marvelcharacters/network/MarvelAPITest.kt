package cossul.anderson.marvelcharacters.network

import cossul.anderson.marvelcharacters.BuildConfig
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MarvelAPITest {

    @Test
    fun getCharacters() {
        val list = MarvelAPI.getCharacters(0)
        assert(list.size > 0)
    }

    @Test
    fun getCharacterComics() {
        val list = MarvelAPI.getCharacterComics(1011334, 0)
        assert(list.size > 0)
    }
}