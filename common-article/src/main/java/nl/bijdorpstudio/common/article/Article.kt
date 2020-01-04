package nl.bijdorpstudio.common.article

import nl.bijdorpstudio.common.data.NonEmptyString
import nl.bijdorpstudio.common.data.Url
import org.threeten.bp.LocalDateTime

data class Article(
    val id: ID,
    val title: NonEmptyString,
    val date: LocalDateTime,
    val imageUrl: Url?
)
