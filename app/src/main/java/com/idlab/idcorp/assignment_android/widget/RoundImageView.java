package com.idlab.idcorp.assignment_android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by diygame5 on 2017-03-27.
 * Project : Assignment_Android
 */

//Causes frame drop. Migrate 'de.hdodenhof:circleimageview:2.1.0'
//Reference : https://github.com/hdodenhof/CircleImageView
@Deprecated
public class RoundImageView extends AppCompatImageView {
    Path clipPath = new Path();
    RectF rect;
    public static float radius = 200.0f;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        radius = this.getWidth() * 1.0f;
        if (rect == null)
            rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
