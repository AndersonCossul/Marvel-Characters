package cossul.anderson.marvelcharacters.network

import android.util.Log
import cossul.anderson.marvelcharacters.models.Character
import cossul.anderson.marvelcharacters.models.ComicSummary
import cossul.anderson.marvelcharacters.models.ComicsList
import cossul.anderson.marvelcharacters.models.Image
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.security.NoSuchAlgorithmException
import javax.net.ssl.HttpsURLConnection

// object to be a singleton
object MarvelAPI {
    private const val TAG = "MarvelAPI"
    private const val BASE_URL = "https://gateway.marvel.com/v1/public"
    private const val PUBLIC_KEY = "563e89dd60d1975d1139130e7b486b2a"
    private const val PRIVATE_KEY = "fa193ef25b25f7a27131cd600ce45fd61f56fdea"

    suspend fun getCharacters(): ArrayList<Character> {
        val charactersString = reachAPI("/characters")
        val charactersList: ArrayList<Character> = ArrayList()
        try {
            val jsonObject = JSONObject(charactersString)
            val data = jsonObject.getJSONObject("data")
            val results = data.getJSONArray("results")
            for (i in 0 until results.length()) {
                val item = results.getJSONObject(i)
                val itemId = item.getInt("id")
                var itemName = item.getString("name")
                var itemDescription = item.getString("description")

                var itemThumbnail = item.getJSONObject("thumbnail")

                var itemThumbnailExtension = itemThumbnail.getString("extension")
                var itemThumbnailPath = itemThumbnail.getString("path") + "/portrait_small.$itemThumbnailExtension"
                var thumbnail = Image(itemThumbnailExtension, itemThumbnailPath)

                var itemLandscapeImageExtension = itemThumbnail.getString("extension")
                var itemLandscapeImagePath = itemThumbnail.getString("path") + "/landscape_xlarge.$itemThumbnailExtension"
                var landscapeImage = Image(itemLandscapeImageExtension, itemLandscapeImagePath)

                var character = Character(itemId, itemName, itemDescription, thumbnail, landscapeImage)

                charactersList.add(character)
            }
        } catch (e: JSONException) {
            Log.e(TAG, e.toString())
        }

        return charactersList
    }

    suspend fun getCharacterComics(characterId: Int): ComicsList {
        val comicsString = reachAPI("/characters/$characterId/comics")
        val comicsSummaryList = ArrayList<ComicSummary>()
        try {
            val jsonObject = JSONObject(comicsString)
            val data = jsonObject.getJSONObject("data")
            val results = data.getJSONArray("results")
            for (i in 0 until results.length()) {
                var item = results.getJSONObject(i)
                var itemTitle = item.getString("title")

                var itemThumbnail = item.getJSONObject("thumbnail")
                var itemThumbnailExtension = itemThumbnail.getString("extension")
                var itemThumbnailPath = itemThumbnail.getString("path")
                var thumbnail = Image(itemThumbnailExtension, itemThumbnailPath)

                var comic = ComicSummary(itemTitle, thumbnail)
                comicsSummaryList.add(comic)
            }
        } catch (e: JSONException) {
            Log.e(TAG, e.toString())
        }

        return ComicsList(comicsSummaryList)
    }

    private fun reachAPI(url: String): String {
        val timestamp = System.currentTimeMillis() / 1000
        val hash = md5("$timestamp$PRIVATE_KEY$PUBLIC_KEY")
        val finalUrl = URL("$BASE_URL$url?ts=$timestamp&apikey=$PUBLIC_KEY&hash=$hash")
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

    private fun md5(s: String): String {
        val md5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = java.security.MessageDigest
                .getInstance(md5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}