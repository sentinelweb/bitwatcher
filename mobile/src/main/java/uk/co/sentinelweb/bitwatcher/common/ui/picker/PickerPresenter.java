package uk.co.sentinelweb.bitwatcher.common.ui.picker;

import android.support.annotation.NonNull;


import java.util.List;

public class PickerPresenter<T> implements PickerContract.Presenter<T> {

    private final @NonNull
    PickerContract.View<T> view;

    private int selectedPosition;
    private List<PickerItemModel<T>> items;

    public PickerPresenter(@NonNull PickerContract.View<T> view) {
        this.view = view;
    }

    @Override
    public void bindData(@NonNull List<PickerItemModel<T>> items) {
        this.items = items;
        view.setData(items);
    }

    @Override
    public void setInitialPosition(int position) {
        selectedPosition = position;
        view.setInitialPosition(selectedPosition);
    }

    @Override
    public void updatePositionFromScroll(int position) {
        selectedPosition = position;
    }

    @Override
    public @NonNull
    PickerItemModel<T> getSelected() {
        return items.get(selectedPosition);
    }
}
