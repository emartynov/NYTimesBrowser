package nl.bijdorpstudio.common.article

import nl.bijdorpstudio.common.data.NonEmptyString
import nl.bijdorpstudio.common.data.Url
import org.threeten.bp.LocalDateTime

data class Article(
    val id: ID,
    val title: NonEmptyString,
    val dateTime: LocalDateTime,
    val imageUrl: Url?
)

val EMPTY_ARTICLE = Article(
    id = ID.of("empty")!!,
    title = NonEmptyString.of("No title")!!,
    dateTime = LocalDateTime.now(),
    imageUrl = null
)
