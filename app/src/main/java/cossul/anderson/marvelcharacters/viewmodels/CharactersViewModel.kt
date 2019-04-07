package cossul.anderson.marvelcharacters.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cossul.anderson.marvelcharacters.models.Character
import cossul.anderson.marvelcharacters.network.MarvelAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel : ViewModel() {
    private val characters: MutableLiveData<List<Character>> by lazy {
        MutableLiveData<List<Character>>().also {
            loadCharacters()
        }
    }

    fun getCharacters(): LiveData<List<Character>> {
        return characters
    }

    // async coroutine in a different thread
    fun loadCharacters(offset: Int = 0) = GlobalScope.launch(Dispatchers.Main) {
        val charactersList = withContext(Dispatchers.Default) { MarvelAPI.getCharacters(offset) }
        val merge = ArrayList<Character>()
        val actualList = characters.value

        if (actualList != null) {
            for (i in 0 until actualList.size) {
                merge.add(actualList[i])
            }
        }

        merge.addAll(charactersList)
        characters.value = merge
    }
}