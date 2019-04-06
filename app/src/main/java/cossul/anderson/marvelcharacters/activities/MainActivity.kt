package cossul.anderson.marvelcharacters.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cossul.anderson.marvelcharacters.R
import recyclerviews.CharactersListAdapter

class MainActivity : AppCompatActivity() {
    private val animals: ArrayList<String> = ArrayList()
    private lateinit var charactersListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addAnimals()

        charactersListRecyclerView = findViewById(R.id.recycler_view)
        charactersListRecyclerView.layoutManager = LinearLayoutManager(this)
        charactersListRecyclerView.adapter = CharactersListAdapter(animals)
    }

    private fun addAnimals() {
        animals.add("Dog")
        animals.add("Cat")
        animals.add("Giraffe")
        animals.add("Elephant")
        animals.add("Leopard")
        animals.add("Bird")
        animals.add("Snake")
    }
}
