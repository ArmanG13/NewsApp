import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R

class FilterActivity : AppCompatActivity() {
    private var selectedCategory: String = "general"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        val applyButton: Button = findViewById(R.id.apply_button)

        applyButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("selectedCategory", selectedCategory)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    fun onCategorySelected(view: View) {
        if (view is RadioButton) {
            selectedCategory = view.text.toString().toLowerCase()
        }
    }
}
