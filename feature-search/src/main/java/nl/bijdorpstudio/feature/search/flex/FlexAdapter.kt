package nl.bijdorpstudio.feature.search.flex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.squareup.picasso.Picasso
import kotlin.properties.Delegates.observable

class FlexAdapter : Adapter<FlexAdapter.FlexViewHolder>() {

    var items: List<Item> by observable(emptyList()) { _, oldItems, newItems ->
        onItemsChanged(
            oldItems,
            newItems
        )
    }

    fun add(item: Item) {
        val newItems = items.toMutableList()
        newItems.add(item)
        items = newItems
    }

    fun add(index: Int, item: Item) {
        val newItems = items.toMutableList()
        newItems.add(index, item)
        items = newItems
    }

    fun clear() {
        items = emptyList()
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlexViewHolder {
        val item = items.first { it.layout == viewType }
        val view = parent.inflate(item.layout)
        return FlexViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlexViewHolder, position: Int) {
        val item = items[position]
        item.onBind(holder.itemView as ViewGroup, Picasso.get())
    }

    class FlexViewHolder(view: View) : RecyclerView.ViewHolder(view)

    open class Item(
        @get:LayoutRes val layout: Int
    ) {

        open fun onBind(@Suppress("UNUSED_PARAMETER") viewGroup: ViewGroup, picasso: Picasso) {
            // Override to implement.
        }

        open fun isItemTheSame(that: Item): Boolean = layout == that.layout

        open fun isContentTheSame(that: Item): Boolean = isItemTheSame(that)
    }

    private fun onItemsChanged(oldItems: List<Item>, newItems: List<Item>) {
        DiffUtil.calculateDiff(FlexDiffUtilCallback(oldItems, newItems))
            .dispatchUpdatesTo(this)
    }

    private fun ViewGroup.inflate(layout: Int): View =
        LayoutInflater.from(this.context).inflate(layout, this, false)
}
