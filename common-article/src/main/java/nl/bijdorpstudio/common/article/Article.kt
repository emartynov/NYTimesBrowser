package nl.bijdorpstudio.common.article

import java.time.LocalDateTime

data class Article(val title: NonEmptyString, val date: LocalDateTime)
