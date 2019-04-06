package recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cossul.anderson.marvelcharacters.R
import models.Character
import com.bumptech.glide.Glide

class CharactersListAdapter(private val items: ArrayList<Character>) :
    RecyclerView.Adapter<CharactersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.characters_list_item, parent, false)
        return ViewHolder(view, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(itemView: View, private val parent: ViewGroup) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(character: Character) {
            val nameTextView = itemView.findViewById(R.id.name) as TextView
            nameTextView.text = character.name

            val thumbnailImageView = itemView.findViewById(R.id.thumbnail) as ImageView
            Glide.with(parent.context).load(character.thumbnail.url).into(thumbnailImageView)
        }
    }
}