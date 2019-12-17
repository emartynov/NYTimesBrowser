package nl.bijdorpstudio.common.search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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
    @Json(name = "_id") val id: String
)
