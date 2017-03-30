package com.idlab.idcorp.assignment_android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by diygame5 on 2017-03-28.
 * Project : Assignment_Android
 */

public class GrayScaleImageView extends AppCompatImageView {
    ColorMatrix matrix = new ColorMatrix();
    ColorMatrixColorFilter cf;

    public GrayScaleImageView(Context context) {
        super(context);
    }

    public GrayScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GrayScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            if (cf == null) {
                matrix.setSaturation(0);
                cf = new ColorMatrixColorFilter(matrix);
            }
            drawable.setColorFilter(cf);
        }
        super.onDraw(canvas);
    }

}
