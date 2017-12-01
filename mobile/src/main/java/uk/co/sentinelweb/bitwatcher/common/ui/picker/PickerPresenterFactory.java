package uk.co.sentinelweb.bitwatcher.common.ui.picker;

import android.support.annotation.NonNull;

import javax.inject.Inject;

public class PickerPresenterFactory {

    @Inject
    public PickerPresenterFactory() {
    }

    public @NonNull <T> PickerContract.Presenter<T> createPresenter(PickerContract.View<T> pickerView) {
        PickerPresenter<T> pickerPresenter = new PickerPresenter<T>(pickerView);
        pickerView.setPresenter(pickerPresenter);
        return pickerPresenter;

    }
}
