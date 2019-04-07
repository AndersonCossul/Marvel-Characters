package cossul.anderson.marvelcharacters.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat.EXTRA_PEOPLE
import androidx.core.widget.NestedScrollView
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
        val nestedScrollView = findViewById<NestedScrollView>(R.id.nested_scroll_view)

        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

            val view = v.getChildAt(v.childCount - 1) as View
            val diff = view.bottom - (v.height + v.scrollY)

            if (diff == 0) {
                Toast.makeText(applicationContext, "Loading more comics", Toast.LENGTH_LONG).show()
                var currentIndex = comicsListAdapter.itemCount
                comicsViewModel.loadComicsFor(character.id, currentIndex)
            }
        })
    }

    private fun mountComicsViewModel() {
        comicsViewModel = ViewModelProviders.of(this).get(ComicsViewModel::class.java)
        comicsViewModel.loadComicsFor(character.id)
        Toast.makeText(this, "Loading comics", Toast.LENGTH_LONG).show() // long because it's overridden on response
        comicsViewModel.getComics().observe(this, Observer<List<ComicSummary>>{ comicsList ->
            comicsListAdapter.setItems(comicsList)

            if (!comicsList.isEmpty()) {
                val comicsTitle = findViewById<TextView>(R.id.comics_title)
                comicsTitle.visibility = View.VISIBLE
                comicsListRecyclerView.visibility = View.VISIBLE
                Toast.makeText(this, "Comics loaded", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No comics found", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
