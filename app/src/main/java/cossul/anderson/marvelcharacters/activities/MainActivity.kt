package cossul.anderson.marvelcharacters.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cossul.anderson.marvelcharacters.R
import models.Character
import models.ComicList
import models.ComicSummary
import models.Image
import recyclerviews.CharactersListAdapter

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
        characters.add(Character(1, "Spider Man", "", Image("jpg", "https://images-na.ssl-images-amazon.com/images/I/61JqKytwchL._SX425_.jpg")))
        characters.add(Character(2, "Spider Man", "", Image("jpg", "https://images-na.ssl-images-amazon.com/images/I/61JqKytwchL._SX425_.jpg")))
        characters.add(Character(3, "Spider Man", "", Image("jpg", "https://images-na.ssl-images-amazon.com/images/I/61JqKytwchL._SX425_.jpg")))
        characters.add(Character(4, "Spider Man", "", Image("jpg", "https://images-na.ssl-images-amazon.com/images/I/61JqKytwchL._SX425_.jpg")))
        characters.add(Character(5, "Spider Man", "", Image("jpg", "https://images-na.ssl-images-amazon.com/images/I/61JqKytwchL._SX425_.jpg")))
        characters.add(Character(6, "Spider Man", "", Image("jpg", "https://images-na.ssl-images-amazon.com/images/I/61JqKytwchL._SX425_.jpg")))
        characters.add(Character(7, "Spider Man", "", Image("jpg", "https://images-na.ssl-images-amazon.com/images/I/61JqKytwchL._SX425_.jpg")))
        characters.add(Character(8, "Spider Man", "", Image("jpg", "https://images-na.ssl-images-amazon.com/images/I/61JqKytwchL._SX425_.jpg")))
        characters.add(Character(9, "Spider Man", "", Image("jpg", "https://images-na.ssl-images-amazon.com/images/I/61JqKytwchL._SX425_.jpg")))
    }
}
