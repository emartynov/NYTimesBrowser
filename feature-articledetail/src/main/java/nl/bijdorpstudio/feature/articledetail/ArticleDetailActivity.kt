package nl.bijdorpstudio.feature.articledetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso


class ArticleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        bindView()
    }

    private fun bindView() {
        val article = DI.article

        supportActionBar?.title = article.title.value

        val imageUrl = article.imageUrl
        if (imageUrl != null) {
            val imageView = findViewById<ImageView>(R.id.article_image)

            Picasso.get()
                .load("https://static01.nyt.com/${imageUrl.value.value}")
                .into(imageView)
        }

        findViewById<TextView>(R.id.article_paragraph).text = article.mainParagraph.value
        findViewById<TextView>(R.id.article_title).text = article.title.value

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val openURLIntent = Intent(Intent.ACTION_VIEW)
            openURLIntent.data = Uri.parse(article.webUrl.value.value)
            startActivity(openURLIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_send, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> {
                shareArticleUrl()
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareArticleUrl() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, DI.article.webUrl.value.value)
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)))
    }
}
