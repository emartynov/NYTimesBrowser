package nl.bijdorpstudio.feature.search

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import nl.bijdorpstudio.common.article.Article
import nl.bijdorpstudio.lib.flex.FlexAdapter
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class ArticleListFlexItem(
    private val article: Article,
    private val onClick: (Article) -> Unit
) :
    FlexAdapter.Item(R.layout.article_list_item) {

    private val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
    private val galleryThumb = android.R.drawable.gallery_thumb

    override fun onBind(viewGroup: ViewGroup, picasso: Picasso) {
        viewGroup.findViewById<TextView>(R.id.article_title).text = article.title.value
        viewGroup.findViewById<TextView>(R.id.article_date).text =
            article.dateTime.format(dateTimeFormatter)

        val imageUrl = article.imageUrl
        val imageView = viewGroup.findViewById<ImageView>(R.id.article_image)
        if (imageUrl != null) {
            val dimens = viewGroup.context.resources.getDimension(R.dimen.article_image_size)

            picasso
                .load("https://static01.nyt.com/${imageUrl.value.value}")
                .placeholder(galleryThumb)
                .resize(dimens.toInt(), dimens.toInt())
                .centerCrop()
                .into(imageView)
        } else {
            picasso.cancelRequest(imageView)
            imageView.setImageResource(galleryThumb)
        }

        viewGroup.setOnClickListener {
            onClick.invoke(article)
        }
    }

    override fun isItemTheSame(that: FlexAdapter.Item): Boolean {
        return super.isItemTheSame(that) && article == (that as ArticleListFlexItem).article
    }
}
