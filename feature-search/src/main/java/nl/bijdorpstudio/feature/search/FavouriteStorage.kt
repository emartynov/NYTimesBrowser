package nl.bijdorpstudio.feature.search

import io.reactivex.Single
import nl.bijdorpstudio.common.article.ID

class FavouriteStorage {
    private val favourites = mutableSetOf<ID>()

    fun favourites(): Single<List<ID>> {
        return Single.just(favourites.toMutableList())
    }

    fun storeFavorite(id: ID) {
        favourites.add(id)
    }
}
