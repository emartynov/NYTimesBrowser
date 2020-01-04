package nl.bijdorpstudio.lib.flex

import androidx.recyclerview.widget.DiffUtil

/**
 * DiffUtil callback for FlexAdapter. Delegates the "sameness" checks to the FlexAdapter items for maximum flexibility.
 */
// TODO: tests
class FlexDiffUtilCallback(
    private val oldItems: List<FlexAdapter.Item>,
    private val newItems: List<FlexAdapter.Item>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].isItemTheSame(newItems[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].isContentTheSame(newItems[newItemPosition])

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size
}
