package cossul.anderson.marvelcharacters.recyclerviewsadapters

import cossul.anderson.marvelcharacters.models.ComicSummary
import cossul.anderson.marvelcharacters.models.Image
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ComicsListAdapterTest {

    @Test
    fun setItems() {
        val list = ArrayList<ComicSummary>()
        list.add(ComicSummary("Avengers - Thanos & Gamora", Image("", "")))
        list.add(ComicSummary("Avengers - Stormbreaker", Image("", "")))

        val adapter = ComicsListAdapter()
        adapter.setItems(list)
        assert(adapter.itemCount == 2)
    }
}