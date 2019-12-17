package nl.bijdorpstudio.common.search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bijdorpstudio.common.article.Article
import nl.bijdorpstudio.common.article.NonEmptyString
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
internal data class ArticleSearchResultDTO(
    val response: ArticleSearchResponseDTO
)

@JsonClass(generateAdapter = true)
internal data class ArticleSearchResponseDTO(
    @Json(name = "docs") val documents: List<ArticleDTO>
)

@JsonClass(generateAdapter = true)
internal data class ArticleDTO(
    @Json(name = "_id") val id: String,
    @Json(name = "pub_date") val date: String,
    val headline: HeadlineDTO,
    val multimedia: List<MultimediaDTO>
)

@JsonClass(generateAdapter = true)
internal data class HeadlineDTO(
    val name: String
)

@JsonClass(generateAdapter = true)
internal data class MultimediaDTO(
    val type: String,
    val subtype: String,
    val url: String
)

internal fun ArticleDTO.toDomain(): Article =
    Article(NonEmptyString.of("test")!!, LocalDateTime.now())
