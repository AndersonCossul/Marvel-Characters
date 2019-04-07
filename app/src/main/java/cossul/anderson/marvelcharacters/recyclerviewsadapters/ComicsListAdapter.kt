package cossul.anderson.marvelcharacters.recyclerviewsadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import cossul.anderson.marvelcharacters.R
import com.bumptech.glide.Glide
import cossul.anderson.marvelcharacters.models.ComicSummary

class ComicsListAdapter : RecyclerView.Adapter<ComicsListAdapter.ViewHolder>() {

    private var items: List<ComicSummary> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comics_list_item, parent, false)
        return ViewHolder(view, parent) // parent em função do Glide precisar do contexto
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<ComicSummary>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, private val parent: ViewGroup) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(comicSummary: ComicSummary) {
            val thumbnailImageView = itemView.findViewById<ImageView>(R.id.comic_thumbnail)
            Glide.with(parent.context).load(comicSummary.thumbnail.path).into(thumbnailImageView)
        }
    }
}