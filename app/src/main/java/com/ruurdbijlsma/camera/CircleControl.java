package com.ruurdbijlsma.camera;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class CircleControl extends View {
    private Paint backgroundPaint=new Paint();
    final private int DefaultBackground = Color.argb(100, 255, 0, 0);
    final private int DefaultColor = Color.WHITE;
    private Paint textPaint = new Paint();
    final private float DefaultSize = 50;

    public CircleControl(Context context) {
        super(context);
        init(null, 0);
    }

    public CircleControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircleControl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray attributes = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircleControl, defStyle, 0);

        int backgroundColor = DefaultBackground;
        Drawable background = getBackground();
        if (background instanceof ColorDrawable) {
            backgroundColor = ((ColorDrawable) background).getColor();
            setBackground(new ColorDrawable(Color.TRANSPARENT));
        }
        backgroundPaint.setColor(backgroundColor);

        int textColor = DefaultColor;
        if (attributes.hasValue(R.styleable.CircleControl_color))
            textColor = attributes.getColor(R.styleable.CircleControl_color, defStyle);
        textPaint.setColor(textColor);

        float size = DefaultSize;
        if(attributes.hasValue(R.styleable.CircleControl_textSize))
            size = attributes.getDimension(R.styleable.CircleControl_textSize, defStyle);
        textPaint.setTextSize(size);

        attributes.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int padding = getPaddingLeft();

        int contentWidth = getWidth();
        int contentHeight = getHeight();

        // Draw the thing.
        double radius = contentWidth / 2 + Math.pow(contentHeight, 2) / (8 * contentWidth);
        double angle = 2 * Math.asin((float) contentHeight / (2 * radius));
        angle = Math.toDegrees(angle);
        int circleY = contentHeight / 2;
        canvas.drawCircle((int) radius, circleY, (int) radius, backgroundPaint);

        double degreesPerTick = 2;
        for (double i = -angle / 2; i <= angle / 2; i += degreesPerTick) {
            canvas.save();
            canvas.rotate((float) i, (float) radius, circleY);
            canvas.drawText("-", padding, circleY, textPaint);
            canvas.restore();
        }
    }
}
