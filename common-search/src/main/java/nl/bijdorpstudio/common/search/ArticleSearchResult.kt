package nl.bijdorpstudio.common.search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bijdorpstudio.common.article.Article
import nl.bijdorpstudio.common.article.NonEmptyString
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
internal data class ArticleSearchResult(
    val response: ArticleSearchResponse
)

@JsonClass(generateAdapter = true)
internal data class ArticleSearchResponse(
    @Json(name = "docs") val documents: List<ArticleDTO>
)

@JsonClass(generateAdapter = true)
internal data class ArticleDTO(
    @Json(name = "_id") val id: String,
    @Json(name = "pub_date") val date: String,
    val headline: Headline,
    val multimedia: List<Multimedia>
)

@JsonClass(generateAdapter = true)
internal data class Headline(
    val name: String
)

@JsonClass(generateAdapter = true)
internal data class Multimedia(
    val type: String,
    val subtype: String,
    val url: String
)

internal fun ArticleDTO.toDomain(): Article =
    Article(NonEmptyString.of("test")!!, LocalDateTime.now())
