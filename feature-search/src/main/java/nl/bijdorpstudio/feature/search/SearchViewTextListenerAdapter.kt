package nl.bijdorpstudio.feature.search

import androidx.appcompat.widget.SearchView

/**
 * Adapter to hide search view boiler plate
 */
open class SearchViewTextListenerAdapter : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String) = false

    override fun onQueryTextChange(newText: String) = false
}
