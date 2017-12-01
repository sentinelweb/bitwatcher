package uk.co.sentinelweb.bitwatcher.common.ui.picker;

import android.support.annotation.NonNull;

import java.util.List;

public interface PickerContract {

    interface Presenter<T> {

        void bindData(@NonNull List<PickerItemModel<T>> items);

        void setInitialPosition(int position);

        void updatePositionFromScroll(int position);

        @NonNull
        PickerItemModel<T> getSelected();
    }

    interface View<T> {

        void setPresenter(@NonNull Presenter<T> presenter);

        void setData(@NonNull List<PickerItemModel<T>> items);

        void setInitialPosition(int position);
    }
}
