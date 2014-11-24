package com.kremski.android.material;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class RippleButton extends Button {

    private RippleEffect rippleEffect;

    public RippleButton(final Context context) {
        super(context);
        init(null);
    }

    public RippleButton(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RippleButton(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        rippleEffect = new RippleEffect(this, attributeSet);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        rippleEffect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rippleEffect.onDraw(canvas);
    }


}
