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
    private fun loadCharacters() = GlobalScope.launch(Dispatchers.Main) {
        val charactersList = withContext(Dispatchers.Default) { MarvelAPI.getCharacters() }
        characters.value = charactersList
    }
}