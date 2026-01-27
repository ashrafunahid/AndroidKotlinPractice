package com.ashrafunahid.imagecrop.callbacks;
import android.graphics.RectF;

public interface OverlayViewChangeListener {

    void onCropRectUpdated(RectF cropRect);

}