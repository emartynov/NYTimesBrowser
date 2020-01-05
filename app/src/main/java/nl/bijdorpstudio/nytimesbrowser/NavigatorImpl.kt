package nl.bijdorpstudio.nytimesbrowser

import android.content.Context
import android.content.Intent
import nl.bijdorpstudio.common.article.Article
import nl.bijdorpstudio.common.navigation.Navigator
import nl.bijdorpstudio.feature.articledetail.ArticleDetailActivity
import nl.bijdorpstudio.feature.articledetail.DI

class NavigatorImpl(private val context: Context) : Navigator {
    override fun showArticleDetail(article: Article) {
        DI.article = article

        val intent = Intent(context, ArticleDetailActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}
