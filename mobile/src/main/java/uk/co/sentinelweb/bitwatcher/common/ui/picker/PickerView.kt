package uk.co.sentinelweb.bitwatcher.common.ui.picker

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.support.annotation.DimenRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ItemDecoration
import android.support.v7.widget.RecyclerView.State
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewGroup
import android.widget.TextView

import uk.co.sentinelweb.bitwatcher.R

class PickerView<T>(view: View) : PickerContract.View<T> {
    private val recyclerView: RecyclerView

    private val context: Context

    private var previousPosition: Int = 0

    private var presenter: PickerContract.Presenter<T>? = null

    init {
        this.context = view.context
        recyclerView = view as RecyclerView
        setupRecyclerView()
    }

    override fun setPresenter(presenter: PickerContract.Presenter<T>) {
        this.presenter = presenter
    }

    override fun setData(ages: List<PickerItemModel<T>>) {
        recyclerView.adapter = ItemViewHolderAdapter(ages)
    }

    override fun setInitialPosition(position: Int) {
        previousPosition = position
        if (recyclerView.measuredHeight > 0) {
            initialSelectionOfPosition(position)
        } else {
            recyclerView.addOnLayoutChangeListener(SelectPositionAfterLayoutListener(position))
        }
    }

    private fun initialSelectionOfPosition(position: Int) {
        goToPosition(position)
        val selectedView = this@PickerView.recyclerView.layoutManager
                .findViewByPosition(position)
        setItemSelected(selectedView)
        recyclerView.addOnScrollListener(SpinnerOnScrollListener())
    }

    private fun goToPosition(position: Int) {
        val itemsHeight = recyclerView.computeVerticalScrollRange() - recyclerView.measuredHeight
        val ypos = (position / recyclerView.adapter.itemCount.toFloat() * itemsHeight).toInt()
        recyclerView.scrollBy(0, ypos - recyclerView.scrollY)
    }

    private fun setupRecyclerView() {
        val layout = LinearLayoutManager(context)
        recyclerView.layoutManager = layout

        // adds item decoration so recycler view can scroll first and last item to the middle for selection
        recyclerView.addItemDecoration(SpinnerEndOffsetToMiddleItemDecoration())
    }

    private fun changeTextSize(tv: TextView, @DimenRes newSizeResource: Int) {
        val newSize = context.resources.getDimensionPixelSize(newSizeResource)
        val animator = ValueAnimator.ofFloat(tv.textSize, newSize.toFloat())
        animator.duration = SIZE_ANIM_DURATION.toLong()
        animator.addUpdateListener(TextSizeAnimationListener(tv))
        animator.start()
    }

    private fun setItemSelected(selectedView: View?) {
        if (selectedView != null) {
            selectedView.isSelected = true
            changeTextSize((selectedView as TextView?)!!, R.dimen.text_size_large)
        }
    }

    private inner class ItemViewHolder internal constructor(internal val view: TextView) : RecyclerView.ViewHolder(view)

    private inner class ItemViewHolderAdapter(private val itemModels: List<PickerItemModel<T>>) : Adapter<ItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(LayoutInflater
                    .from(context).inflate(R.layout.view_picker_item, parent, false) as TextView)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            holder.view.isSelected = false
            holder.view.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.resources.getDimensionPixelSize(R.dimen.text_size_medium).toFloat())
            holder.view.text = itemModels[position].display
        }

        override fun getItemCount(): Int {
            return itemModels.size
        }
    }

    private inner class SpinnerOnScrollListener : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val centerView = this@PickerView.recyclerView.findChildViewUnder(
                    (this@PickerView.recyclerView.measuredWidth / 2).toFloat(),
                    (this@PickerView.recyclerView.measuredHeight / 2).toFloat())
            val centerPos = this@PickerView.recyclerView.getChildAdapterPosition(centerView)

            if (centerPos > -1 && previousPosition != centerPos) {
                val previouslySelectedView = this@PickerView.recyclerView.layoutManager
                        .findViewByPosition(previousPosition)
                if (previouslySelectedView != null) {
                    previouslySelectedView.isSelected = false
                    changeTextSize(previouslySelectedView as TextView, R.dimen.text_size_medium)
                }

                setItemSelected(centerView)

                previousPosition = centerPos
                presenter!!.updatePositionFromScroll(previousPosition) // is current at the moment
            }
        }
    }

    private inner class SpinnerEndOffsetToMiddleItemDecoration : ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                    state: State?) {
            val viewPosition = recyclerView.getChildAdapterPosition(view)
            if (viewPosition == 0) {
                outRect.top = (recyclerView.height - view.height) / 2
            } else if (viewPosition == parent.adapter.itemCount - 1) {
                outRect.bottom = (recyclerView.height - view.height) / 2
            } else {
                super.getItemOffsets(outRect, view, parent, state)
            }
        }
    }

    private inner class SelectPositionAfterLayoutListener(private val position: Int) : OnLayoutChangeListener {

        override fun onLayoutChange(v: View, left: Int, top: Int, right: Int,
                                    bottom: Int,
                                    oldLeft: Int,
                                    oldTop: Int, oldRight: Int, oldBottom: Int) {
            recyclerView.removeOnLayoutChangeListener(this)
            initialSelectionOfPosition(position)
        }
    }

    private class TextSizeAnimationListener(private val tv: TextView) : ValueAnimator.AnimatorUpdateListener {

        override fun onAnimationUpdate(valueAnimator: ValueAnimator) {
            val animatedValue = valueAnimator.animatedValue as Float
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, animatedValue)
        }
    }

    companion object {

        private val SIZE_ANIM_DURATION = 100
    }
}
