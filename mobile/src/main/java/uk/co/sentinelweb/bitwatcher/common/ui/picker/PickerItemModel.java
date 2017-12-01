package uk.co.sentinelweb.bitwatcher.common.ui.picker;

import android.support.annotation.NonNull;

public class PickerItemModel<T> {

    public final @NonNull
    String display;
    public final @NonNull
    T value;

    public PickerItemModel(@NonNull String display, @NonNull T value) {
        this.display = display;
        this.value = value;
    }

}
