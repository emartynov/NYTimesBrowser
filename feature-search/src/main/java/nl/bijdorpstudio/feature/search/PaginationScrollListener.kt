package nl.bijdorpstudio.feature.search

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: test
class PaginationScrollListener(
    recyclerView: RecyclerView,
    private val onReachingEnd: () -> Unit,
    private val onEndReached: (() -> Unit)? = null
) : RecyclerView.OnScrollListener() {

    private var isReachingEnd = false
    private var isEndReached = false

    private val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
        ?: throw IllegalStateException(
            "Pagination was enabled before adding a LinearLayoutManager to the RecyclerView"
        )

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) = Unit

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val lastItemPosition = layoutManager.itemCount - 1

        setReachingEnd(lastVisibleItemPosition + ITEM_OFFSET >= lastItemPosition)

        setEndReached(lastVisibleItemPosition == lastItemPosition)
    }

    private fun setReachingEnd(value: Boolean) {
        if (value != isReachingEnd) {
            isReachingEnd = value

            if (isReachingEnd) {
                onReachingEnd.invoke()
            }
        }
    }

    private fun setEndReached(value: Boolean) {
        if (value != isEndReached) {
            isEndReached = value

            if (isEndReached) {
                onEndReached?.invoke()
            }
        }
    }

    companion object {
        /**
         * The number of items which can be between the last item before more data is requested.
         */
        //TODO: rethink number of items
        private const val ITEM_OFFSET = 10
    }
}
