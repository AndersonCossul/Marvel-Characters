package cossul.anderson.marvelcharacters.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat.EXTRA_PEOPLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cossul.anderson.marvelcharacters.R
import cossul.anderson.marvelcharacters.models.Character
import cossul.anderson.marvelcharacters.recyclerviewsadapters.CharactersListAdapter
import cossul.anderson.marvelcharacters.utils.InfiniteScrollListener
import cossul.anderson.marvelcharacters.viewmodels.CharactersViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var charactersListRecyclerView: RecyclerView
    private lateinit var charactersListAdapter: CharactersListAdapter
    private lateinit var charactersViewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mountList()
        mountViewModel()
    }

    private fun mountList() {
        charactersListAdapter = CharactersListAdapter { character: Character -> characterItemClicked(character) }
        charactersListRecyclerView = findViewById(R.id.recycler_view)
        val linearLayout = LinearLayoutManager(this)
        charactersListRecyclerView.layoutManager = linearLayout
        charactersListRecyclerView.adapter = charactersListAdapter
        charactersListRecyclerView.addOnScrollListener(InfiniteScrollListener({
            Toast.makeText(applicationContext, "Loading more characters", Toast.LENGTH_LONG).show()
            val currentIndex = charactersListAdapter.itemCount
            charactersViewModel.loadCharacters(currentIndex)
        }, linearLayout))
    }

    private fun mountViewModel() {
        charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel::class.java)
        charactersViewModel.getCharacters().observe(this, Observer<List<Character>>{ charactersList ->
            charactersListAdapter.setItems(charactersList)
            if (charactersList.isEmpty()) {
                Toast.makeText(applicationContext, "No characters found", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "New characters loaded", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun characterItemClicked(character: Character) {
        val intent = Intent(this, CharacterDetailsActivity::class.java)
        intent.putExtra(EXTRA_PEOPLE, character)
        startActivity(intent)
    }
}
