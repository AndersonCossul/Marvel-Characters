package cossul.anderson.marvelcharacters.recyclerviewsadapters

import android.media.Image
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

import cossul.anderson.marvelcharacters.models.*

@RunWith(RobolectricTestRunner::class)
class CharactersListAdapterTest {

    @Test
    fun setItems() {
        val list = ArrayList<Character>()
        list.add(Character(0, "Anderson", "des", Image("", ""), Image("", "")))
        list.add(Character(0, "Cossul", "des", Image("", ""), Image("", "")))

        val adapter = CharactersListAdapter {  }
        adapter.setItems(list)
        assert(adapter.itemCount == 2)
    }
}