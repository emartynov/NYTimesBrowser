package nl.bijdorpstudio.feature.search

import android.view.ViewGroup
import android.widget.TextView
import nl.bijdorpstudio.common.article.Article
import nl.bijdorpstudio.feature.search.flex.FlexAdapter
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

// TODO: test
class ArticleListFlexItem(private val article: Article) :
    FlexAdapter.Item(R.layout.article_list_item) {

    private val dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)

    override fun onBind(viewGroup: ViewGroup) {
        viewGroup.findViewById<TextView>(R.id.article_title).text = article.title.value
        viewGroup.findViewById<TextView>(R.id.article_date).text =
            article.date.format(dateTimeFormatter)
    }

    override fun isItemTheSame(that: FlexAdapter.Item): Boolean {
        return super.isItemTheSame(that) && article == (that as ArticleListFlexItem).article
    }
}
