package nl.bijdorpstudio.common.navigation

import nl.bijdorpstudio.common.article.Article

interface Navigator {
    fun showArticleDetail(article: Article)
}
