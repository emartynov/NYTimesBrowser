package nl.bijdorpstudio.feature.search.flex

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

open class FlexRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    protected open val flexAdapter = FlexAdapter()

    @CallSuper
    open fun setItems(items: List<FlexAdapter.Item>?) {
        flexAdapter.items = items ?: emptyList()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        adapter = flexAdapter
    }

    override fun onDetachedFromWindow() {
        swapAdapter(null, true)
        super.onDetachedFromWindow()
    }
}
