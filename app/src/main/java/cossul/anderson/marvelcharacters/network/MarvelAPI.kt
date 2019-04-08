package cossul.anderson.marvelcharacters.network

import android.util.Log
import cossul.anderson.marvelcharacters.models.Character
import cossul.anderson.marvelcharacters.models.ComicSummary
import cossul.anderson.marvelcharacters.models.Image
import cossul.anderson.marvelcharacters.utils.Encryption
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.io.BufferedReader
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

// object to be a singleton
object MarvelAPI {
    private const val TAG = "MarvelAPI"
    private const val BASE_URL = "https://gateway.marvel.com/v1/public"
    private const val PUBLIC_KEY = "563e89dd60d1975d1139130e7b486b2a"
    private const val PRIVATE_KEY = "fa193ef25b25f7a27131cd600ce45fd61f56fdea"

    fun getCharacters(offset: Int): ArrayList<Character> {
        val charactersString = reachAPI("/characters", offset)
        val charactersList: ArrayList<Character> = ArrayList()
        try {
            val jsonObject = JSONObject(charactersString)
            val data = jsonObject.getJSONObject("data")
            val results = data.getJSONArray("results")
            for (i in 0 until results.length()) {
                val item = results.getJSONObject(i)
                val itemId = item.getInt("id")
                val itemName = item.getString("name")
                val itemDescription = item.getString("description")

                val itemThumbnail = item.getJSONObject("thumbnail")

                val itemThumbnailExtension = itemThumbnail.getString("extension")
                val itemThumbnailPath = itemThumbnail.getString("path") + "/portrait_small.$itemThumbnailExtension"
                val thumbnail = Image(itemThumbnailExtension, itemThumbnailPath)

                val itemLandscapeImageExtension = itemThumbnail.getString("extension")
                val itemLandscapeImagePath = itemThumbnail.getString("path") + "/landscape_xlarge.$itemThumbnailExtension"
                val landscapeImage = Image(itemLandscapeImageExtension, itemLandscapeImagePath)

                val character = Character(itemId, itemName, itemDescription, thumbnail, landscapeImage)

                charactersList.add(character)
            }
        } catch (e: JSONException) {
            Log.e(TAG, e.toString())
        }

        return charactersList
    }

    fun getCharacterComics(characterId: Int, offset: Int): ArrayList<ComicSummary> {
        val comicsString = reachAPI("/characters/$characterId/comics", offset)
        val comicsSummaryList = ArrayList<ComicSummary>()
        try {
            val jsonObject = JSONObject(comicsString)
            val data = jsonObject.getJSONObject("data")
            val results = data.getJSONArray("results")
            for (i in 0 until results.length()) {
                val item = results.getJSONObject(i)
                val itemTitle = item.getString("title")

                val itemThumbnail = item.getJSONObject("thumbnail")
                val itemThumbnailExtension = itemThumbnail.getString("extension")
                val itemThumbnailPath = itemThumbnail.getString("path") + "/standard_medium.$itemThumbnailExtension"
                val thumbnail = Image(itemThumbnailExtension, itemThumbnailPath)

                val comic = ComicSummary(itemTitle, thumbnail)
                comicsSummaryList.add(comic)
            }
        } catch (e: JSONException) {
            Log.e(TAG, e.toString())
        }

        return comicsSummaryList
    }

    private fun reachAPI(url: String, offset: Int): String {
        val timestamp = System.currentTimeMillis() / 1000
        val hash = Encryption.md5("$timestamp$PRIVATE_KEY$PUBLIC_KEY")
        val finalUrl = URL("$BASE_URL$url?ts=$timestamp&apikey=$PUBLIC_KEY&hash=$hash&offset=$offset")
        var resultString = ""

        try {
            val conn = finalUrl.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"

            val responseCode = conn.responseCode

            resultString = if (responseCode == HttpsURLConnection.HTTP_OK) {
                val allTextResponse = conn.inputStream.bufferedReader().use(BufferedReader::readText)
                allTextResponse
            } else {
                ""
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
        return resultString
    }
}