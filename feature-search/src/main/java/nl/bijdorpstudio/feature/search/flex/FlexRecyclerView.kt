package nl.bijdorpstudio.feature.search.flex

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FlexRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val flexAdapter = FlexAdapter()

    init {
        layoutManager = LinearLayoutManager(context)
    }

    fun setItems(items: List<FlexAdapter.Item>?) {
        flexAdapter.items = items ?: emptyList()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        adapter = flexAdapter
    }

    override fun onDetachedFromWindow() {
        // no memory leaks
        swapAdapter(null, true)
        super.onDetachedFromWindow()
    }
}
