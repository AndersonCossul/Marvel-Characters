package cossul.anderson.marvelcharacters.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cossul.anderson.marvelcharacters.R
import cossul.anderson.marvelcharacters.models.Character
import cossul.anderson.marvelcharacters.recyclerviewsadapters.CharactersListAdapter
import cossul.anderson.marvelcharacters.viewmodels.CharactersViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var charactersListRecyclerView: RecyclerView
    private lateinit var charactersListAdapter: CharactersListAdapter
    private lateinit var charactersViewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        charactersListAdapter = CharactersListAdapter()
        charactersListRecyclerView = findViewById(R.id.recycler_view)
        charactersListRecyclerView.layoutManager = LinearLayoutManager(this)
        charactersListRecyclerView.adapter = charactersListAdapter

        charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        charactersViewModel.getCharacters().observe(this, Observer<List<Character>>{ charactersList ->
            charactersListAdapter.setItems(charactersList)
            if (charactersList.isEmpty()) {
                Toast.makeText(applicationContext, "No characters found", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
