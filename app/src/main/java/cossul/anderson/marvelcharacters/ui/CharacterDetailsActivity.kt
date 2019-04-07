package cossul.anderson.marvelcharacters.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat.EXTRA_PEOPLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cossul.anderson.marvelcharacters.R
import cossul.anderson.marvelcharacters.models.Character
import cossul.anderson.marvelcharacters.models.ComicSummary
import cossul.anderson.marvelcharacters.recyclerviewsadapters.ComicsListAdapter
import cossul.anderson.marvelcharacters.viewmodels.ComicsViewModel

class CharacterDetailsActivity : AppCompatActivity() {
    private lateinit var comicsListRecyclerView: RecyclerView
    private lateinit var comicsListAdapter: ComicsListAdapter
    private lateinit var comicsViewModel: ComicsViewModel
    private lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        mountInfo()
        mountComicsList()
        mountComicsViewModel()
    }

    private fun mountInfo() {
        character = intent.extras.getSerializable(EXTRA_PEOPLE) as Character

        val thumbnail = findViewById<ImageView>(R.id.thumbnail)
        val name = findViewById<TextView>(R.id.name)
        val description = findViewById<TextView>(R.id.description)

        Glide.with(this).load(character.landscapeImage.path).into(thumbnail)
        name.text = character.name
        description.text = if (character.description.isEmpty()) {
            "Mysterious wolf.."
        } else {
            character.description
        }
    }

    private fun mountComicsList() {
        comicsListAdapter = ComicsListAdapter()
        comicsListRecyclerView = findViewById(R.id.recycler_view)
        comicsListRecyclerView.layoutManager = GridLayoutManager(this, 3)
        comicsListRecyclerView.adapter = comicsListAdapter
        comicsListRecyclerView.isNestedScrollingEnabled = false
    }

    private fun mountComicsViewModel() {
        comicsViewModel = ViewModelProviders.of(this).get(ComicsViewModel::class.java)
        comicsViewModel.loadComicsFor(character.id)
        comicsViewModel.getComics().observe(this, Observer<List<ComicSummary>>{ comicsList ->
            comicsListAdapter.setItems(comicsList)

            if (!comicsList.isEmpty()) {
                val comicsTitle = findViewById<TextView>(R.id.comics_title)
                comicsTitle.visibility = View.VISIBLE
                comicsListRecyclerView.visibility = View.VISIBLE
            }
        })
    }
}
