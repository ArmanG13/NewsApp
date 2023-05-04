import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.bumptech.glide.Glide


class DetailsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val article: Article? = intent.getParcelableExtra("article")

        val titleTextView: TextView = findViewById(R.id.article_title)
        val sourceTextView: TextView = findViewById(R.id.article_source)
        val authorTextView: TextView = findViewById(R.id.article_author)
        val descriptionTextView: TextView = findViewById(R.id.article_description)
        val imageView: ImageView = findViewById(R.id.article_image)

        titleTextView.text = article?.title
        sourceTextView.text = article?.source
        authorTextView.text = article?.author
        descriptionTextView.text = article?.description

        Glide.with(this)
            .load(article?.imageUrl)
            .placeholder(R.drawable.placeholder)
            .into(imageView)
    }
}
