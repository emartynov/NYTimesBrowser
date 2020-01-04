package nl.bijdorpstudio.feature.search

import android.view.ViewGroup
import android.widget.TextView
import nl.bijdorpstudio.common.article.Article
import nl.bijdorpstudio.feature.search.flex.FlexAdapter

// TODO: test
class ArticleListFlexItem(private val article: Article) :
    FlexAdapter.Item(R.layout.article_list_item) {

    override fun onBind(viewGroup: ViewGroup) {
        super.onBind(viewGroup)

        viewGroup.findViewById<TextView>(R.id.article_title).text = article.title.value
        viewGroup.findViewById<TextView>(R.id.article_date).text = article.date.toString()
    }

    override fun isItemTheSame(that: FlexAdapter.Item): Boolean {
        return super.isItemTheSame(that) && article == (that as ArticleListFlexItem).article
    }
}
