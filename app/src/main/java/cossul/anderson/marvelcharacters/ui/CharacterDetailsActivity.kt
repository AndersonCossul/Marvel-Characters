package cossul.anderson.marvelcharacters.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat.EXTRA_PEOPLE
import com.bumptech.glide.Glide
import cossul.anderson.marvelcharacters.R
import cossul.anderson.marvelcharacters.models.Character

class CharacterDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        val character = intent.extras.getSerializable(EXTRA_PEOPLE) as? Character
        val thumbnailImageView = findViewById<ImageView>(R.id.thumbnail)
        val nameTextView = findViewById<TextView>(R.id.name)
        val descriptionTextView = findViewById<TextView>(R.id.description)

        Glide.with(this).load(character?.landscapeImage?.path).into(thumbnailImageView)
        nameTextView.text = character?.name
        descriptionTextView.text = if (character?.description?.isEmpty() == true) {
            "Mysterious wolf.."
        } else {
            character?.description
        }
    }
}
