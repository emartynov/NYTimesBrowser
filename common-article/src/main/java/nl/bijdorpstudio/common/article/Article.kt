package nl.bijdorpstudio.common.article

import org.threeten.bp.LocalDateTime

data class Article(
    val id: ID,
    val title: NonEmptyString,
    val date: LocalDateTime,
    val imageUrl: Url?
)
