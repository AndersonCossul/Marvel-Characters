package cossul.anderson.marvelcharacters.recyclerviewsadapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cossul.anderson.marvelcharacters.R
import cossul.anderson.marvelcharacters.models.Character
import com.bumptech.glide.Glide

class CharactersListAdapter(private val clickListener: (Character) -> Unit) :
    RecyclerView.Adapter<CharactersListAdapter.ViewHolder>() {

    private var items: List<Character> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.characters_list_item, parent, false)
        return ViewHolder(view, parent, clickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("CharatersListAdapter", "position: $position")
        holder.bind(items[position])
    }

    fun setItems(items: List<Character>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, private val parent: ViewGroup, val clickListener: (Character) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(character: Character) {
            val nameTextView = itemView.findViewById(R.id.name) as TextView
            nameTextView.text = character.name

            val thumbnailImageView = itemView.findViewById(R.id.thumbnail) as ImageView
            Glide.with(parent.context).load(character.thumbnail.path).into(thumbnailImageView)

            itemView.setOnClickListener { clickListener(character) }
        }
    }
}