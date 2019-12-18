package nl.bijdorpstudio.feature.search.flex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlin.properties.Delegates.observable

open class FlexAdapter : Adapter<FlexAdapter.FlexViewHolder>() {

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

    override fun getItemViewType(position: Int) = items[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlexViewHolder {
        val item = items.first { it.type == viewType }
        val view = parent.inflate(item.layout)
        return FlexViewHolder(item.type, view)
    }

    override fun onBindViewHolder(holder: FlexViewHolder, position: Int) {
        val item = items[position]
        item.onBind(holder.itemView as ViewGroup)
    }

    class FlexViewHolder(val type: Int, view: View) : RecyclerView.ViewHolder(view)

    abstract class Item(
        @get:LayoutRes val layout: Int,
        val type: Int
    ) {

        fun onBind(@Suppress("UNUSED_PARAMETER") viewGroup: ViewGroup) {
            // Override to implement.
        }

        fun isItemTheSame(that: Item): Boolean = type == that.type

        fun isContentTheSame(that: Item): Boolean = isItemTheSame(that)
    }

    private fun onItemsChanged(oldItems: List<Item>, newItems: List<Item>) {
        DiffUtil.calculateDiff(FlexDiffUtilCallback(oldItems, newItems)).dispatchUpdatesTo(this)
    }

    private fun ViewGroup.inflate(layout: Int): View =
        LayoutInflater.from(this.context).inflate(layout, this, false)
}
