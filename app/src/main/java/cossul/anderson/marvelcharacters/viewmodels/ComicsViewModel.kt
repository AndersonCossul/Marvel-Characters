package cossul.anderson.marvelcharacters.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cossul.anderson.marvelcharacters.models.Character
import cossul.anderson.marvelcharacters.models.ComicSummary
import cossul.anderson.marvelcharacters.models.Image
import cossul.anderson.marvelcharacters.network.MarvelAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComicsViewModel : ViewModel() {
    private val comicsList: MutableLiveData<List<ComicSummary>> = MutableLiveData()

    fun getComics(): LiveData<List<ComicSummary>> {
        return comicsList
    }

    // async coroutine in a different thread
    fun loadComicsFor(characterId: Int, offset: Int = 0) = GlobalScope.launch(Dispatchers.Main) {
        val list = withContext(Dispatchers.Default) { MarvelAPI.getCharacterComics(characterId, offset) }

        val actualList = comicsList.value
        val merge = ArrayList<ComicSummary>()

        if (actualList != null) {
            for (i in 0 until actualList.size) {
                merge.add(actualList[i])
            }
        }
        merge.addAll(list)
        comicsList.value = merge
    }
}