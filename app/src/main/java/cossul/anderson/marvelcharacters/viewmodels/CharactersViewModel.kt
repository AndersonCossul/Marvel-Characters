package cossul.anderson.marvelcharacters.viewmodels

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cossul.anderson.marvelcharacters.models.Character
import cossul.anderson.marvelcharacters.models.Image

class CharactersViewModel : ViewModel() {
    private val characters: MutableLiveData<List<Character>> by lazy {
        MutableLiveData<List<Character>>().also {
            loadCharacters()
        }
    }

    fun getCharacters(): LiveData<List<Character>> {
        return characters
    }

    private fun loadCharacters() {
        // this has to be async
        val asyncHandler = Handler()
        asyncHandler.postDelayed({
            val list = ArrayList<Character>()
            list.add(Character(0, "Dog", "", Image("jpg", "https://www.nationalgeographic.com/content/dam/animals/thumbs/rights-exempt/mammals/d/domestic-dog_thumb.jpg")))
            list.add(Character(0, "Cat", "", Image("jpg", "https://news.nationalgeographic.com/content/dam/news/2018/05/17/you-can-train-your-cat/02-cat-training-NationalGeographic_1484324.jpg")))
            list.add(Character(0, "Shark", "", Image("jpg", "https://a57.foxnews.com/a57.foxnews.com/static.foxnews.com/foxnews.com/content/uploads/2018/09/640/320/1862/1048/Great20White20Shark.jpg?ve=1&tl=1?ve=1&tl=1")))
            characters.value = list
        }, 1000)
    }
}