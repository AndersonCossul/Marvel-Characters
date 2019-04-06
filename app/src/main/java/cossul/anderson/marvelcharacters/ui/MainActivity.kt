package cossul.anderson.marvelcharacters.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cossul.anderson.marvelcharacters.R
import cossul.anderson.marvelcharacters.models.Character
import cossul.anderson.marvelcharacters.models.Image
import cossul.anderson.marvelcharacters.recyclerviews.CharactersListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var charactersListRecyclerView: RecyclerView
    private val characters: ArrayList<Character> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addCharacters()

        charactersListRecyclerView = findViewById(R.id.recycler_view)
        charactersListRecyclerView.layoutManager = LinearLayoutManager(this)
        charactersListRecyclerView.adapter = CharactersListAdapter(characters)
    }

    private fun addCharacters() {
        characters.add(Character(0, "Spider Man", "", Image("jpg", "https://images-na.ssl-images-amazon.com/images/I/61JqKytwchL._SX425_.jpg")))
        characters.add(Character(1, "Thanos", "", Image("jpg", "https://www.sideshow.com/storage/product-images/903766/thanos_marvel_silo.png")))
        characters.add(Character(2, "Iron Man", "", Image("jpg", "https://www.sideshow.com/storage/product-images/903341/iron-man-mark-iv_marvel_silo_sm.png")))
    }
}
