package nl.bijdorpstudio.common.search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nl.bijdorpstudio.common.article.Article
import nl.bijdorpstudio.common.article.ID
import nl.bijdorpstudio.common.data.NonEmptyString
import nl.bijdorpstudio.common.data.Url
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

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
    @Json(name = "lead_paragraph") val mainParagraph: String,
    @Json(name = "web_url") val url: String,
    val multimedia: List<MultimediaDTO>
)

@JsonClass(generateAdapter = true)
internal data class HeadlineDTO(
    val main: String
)

@JsonClass(generateAdapter = true)
internal data class MultimediaDTO(
    val type: String,
    val subtype: String,
    val url: String
)

// all values should be in json
internal fun ArticleDTO.toDomain(): Article {
    val slideImageMultimedia = multimedia.find { it.type == "image" && it.subtype == "slide" }
    return Article(
        id = ID.of(this.id)!!,
        title = NonEmptyString.of(headline.main)!!,
        dateTime = LocalDateTime.parse(
            date,
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        ),
        mainParagraph = NonEmptyString.of(mainParagraph)!!,
        webUrl = Url.of(url)!!,
        imageUrl = if (slideImageMultimedia?.url != null) Url.of(slideImageMultimedia.url) else null
    )
}
