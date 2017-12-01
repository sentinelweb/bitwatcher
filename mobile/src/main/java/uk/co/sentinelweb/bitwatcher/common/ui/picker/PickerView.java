package uk.co.sentinelweb.bitwatcher.common.ui.picker;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uk.co.sentinelweb.bitwatcher.R;

public class PickerView<T> implements PickerContract.View<T> {

    private static final int SIZE_ANIM_DURATION = 100;
    private final RecyclerView recyclerView;

    private final Context context;

    private int previousPosition;

    private PickerContract.Presenter<T> presenter;

    public PickerView(@NonNull View view) {
        this.context = view.getContext();
        recyclerView = (RecyclerView) view;
        setupRecyclerView();
    }

    @Override
    public void setPresenter(@NonNull PickerContract.Presenter<T> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(@NonNull List<PickerItemModel<T>> ages) {
        recyclerView.setAdapter(new ItemViewHolderAdapter(ages));
    }

    @Override
    public void setInitialPosition(int position) {
        previousPosition = position;
        if (recyclerView.getMeasuredHeight() > 0) {
            initialSelectionOfPosition(position);
        } else {
            recyclerView.addOnLayoutChangeListener(new SelectPositionAfterLayoutListener(position));
        }
    }

    private void initialSelectionOfPosition(int position) {
        goToPosition(position);
        View selectedView = PickerView.this.recyclerView.getLayoutManager()
                .findViewByPosition(position);
        setItemSelected(selectedView);
        recyclerView.addOnScrollListener(new SpinnerOnScrollListener());
    }

    private void goToPosition(int position) {
        int itemsHeight = recyclerView.computeVerticalScrollRange() - recyclerView.getMeasuredHeight();
        int ypos = (int) ((position / (float) recyclerView.getAdapter().getItemCount()) * itemsHeight);
        recyclerView.scrollBy(0, ypos - recyclerView.getScrollY());
    }

    private void setupRecyclerView() {
        final LinearLayoutManager layout = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layout);

        // adds item decoration so recycler view can scroll first and last item to the middle for selection
        recyclerView.addItemDecoration(new SpinnerEndOffsetToMiddleItemDecoration());
    }

    private void changeTextSize(@NonNull TextView tv, @DimenRes int newSizeResource) {
        int newSize = context.getResources().getDimensionPixelSize(newSizeResource);
        ValueAnimator animator = ValueAnimator.ofFloat(tv.getTextSize(), newSize);
        animator.setDuration(SIZE_ANIM_DURATION);
        animator.addUpdateListener(new TextSizeAnimationListener(tv));
        animator.start();
    }

    private void setItemSelected(View selectedView) {
        if (selectedView != null) {
            selectedView.setSelected(true);
            changeTextSize((TextView) selectedView, R.dimen.text_size_large);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        final TextView view;

        ItemViewHolder(@NonNull TextView view) {
            super(view);
            this.view = view;
        }
    }

    private class ItemViewHolderAdapter extends Adapter<ItemViewHolder> {

        private final List<PickerItemModel<T>> itemModels;

        public ItemViewHolderAdapter(@NonNull List<PickerItemModel<T>> itemModels) {
            this.itemModels = itemModels;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder((TextView) LayoutInflater
                    .from(context).inflate(R.layout.view_picker_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.view.setSelected(false);
            holder.view.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    context.getResources().getDimensionPixelSize(R.dimen.text_size_medium));
            holder.view.setText(itemModels.get(position).display);
        }

        @Override
        public int getItemCount() {
            return itemModels.size();
        }
    }

    private class SpinnerOnScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            View centerView = PickerView.this.recyclerView.findChildViewUnder(
                    PickerView.this.recyclerView.getMeasuredWidth() / 2,
                    PickerView.this.recyclerView.getMeasuredHeight() / 2);
            int centerPos = PickerView.this.recyclerView.getChildAdapterPosition(centerView);

            if (centerPos > -1 && previousPosition != centerPos) {
                View previouslySelectedView = PickerView.this.recyclerView.getLayoutManager()
                        .findViewByPosition(previousPosition);
                if (previouslySelectedView != null) {
                    previouslySelectedView.setSelected(false);
                    changeTextSize((TextView) previouslySelectedView, R.dimen.text_size_medium);
                }

                setItemSelected(centerView);

                previousPosition = centerPos;
                presenter.updatePositionFromScroll(previousPosition); // is current at the moment
            }
        }
    }

    private class SpinnerEndOffsetToMiddleItemDecoration extends ItemDecoration {

        @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                             State state) {
            int viewPosition = recyclerView.getChildAdapterPosition(view);
            if (viewPosition == 0) {
                outRect.top = (recyclerView.getHeight() - view.getHeight()) / 2;
            } else if (viewPosition == parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = (recyclerView.getHeight() - view.getHeight()) / 2;
            } else {
                super.getItemOffsets(outRect, view, parent, state);
            }
        }
    }

    private class SelectPositionAfterLayoutListener implements OnLayoutChangeListener {

        private final int position;

        public SelectPositionAfterLayoutListener(int position) {
            this.position = position;
        }

        @Override public void onLayoutChange(View v, int left, int top, int right,
                                             int bottom,
                                             int oldLeft,
                                             int oldTop, int oldRight, int oldBottom) {
            recyclerView.removeOnLayoutChangeListener(this);
            initialSelectionOfPosition(position);
        }
    }

    private static class TextSizeAnimationListener implements ValueAnimator.AnimatorUpdateListener {

        private final TextView tv;

        public TextSizeAnimationListener(@NonNull TextView tv) {
            this.tv = tv;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float animatedValue = (float) valueAnimator.getAnimatedValue();
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, animatedValue);
        }
    }
}
