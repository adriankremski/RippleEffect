package com.github.snuffix.android.material;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.github.snuffix.android.material.R;

public class RippleEffect {

    private Paint rippleCirclePaint;
    private Paint fadeOutAnimationColorPaint;

    private boolean rippleAnimationStarted = false;
    private boolean fadeOutAnimationStarted = false;

    private float radius;
    private float touchedPointX;
    private float touchedPointY;

    private View view;

    private final static int RIPPLE_ANIMATION_DURATION_IN_MS = 300;
    private final static int RIPPLE_ANIMATION_COLOR = Color.parseColor("#44000000");

    private final static int FADE_OUT_ANIMATION_DURATION_IN_MS = 500;
    private final static int FADE_OUT_ANIMATION_COLOR = Color.BLACK;

    // Alpha from 0-255
    private final static int FADE_OUT_ANIMATION_START_ALPHA = 204;

    private long rippleAnimationDuration;
    private long fadeOutAnimationDuration;
    private int rippleAnimationColor;
    private int fadeOutAnimationColor;
    private int fadeOutAnimationStartAlpha;

    private ObjectAnimator currentAnimation;
    private boolean isRippleAnimationCanceled;

    public RippleEffect(View view, AttributeSet attrs) {
        this.view = view;

        initAttributes(attrs);

        rippleCirclePaint = new Paint();
        rippleCirclePaint.setColor(rippleAnimationColor);
        rippleCirclePaint.setStrokeWidth(1);
        rippleCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        fadeOutAnimationColorPaint = new Paint();
        fadeOutAnimationColorPaint.setColor(fadeOutAnimationColor);
        fadeOutAnimationColorPaint.setStrokeWidth(1);
        fadeOutAnimationColorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray customAttrs = view.getContext().obtainStyledAttributes(attrs, R.styleable.RippleView);

        rippleAnimationColor = customAttrs.getColor(R.styleable.RippleView_rippleAnimationColor, RIPPLE_ANIMATION_COLOR);
        fadeOutAnimationColor = customAttrs.getColor(R.styleable.RippleView_fadeOutAnimationColor, FADE_OUT_ANIMATION_COLOR);

        rippleAnimationDuration = customAttrs.getInteger(R.styleable.RippleView_rippleAnimationDuration, RIPPLE_ANIMATION_DURATION_IN_MS);
        fadeOutAnimationDuration = customAttrs.getInteger(R.styleable.RippleView_fadeOutAnimationDuration, FADE_OUT_ANIMATION_DURATION_IN_MS);

        fadeOutAnimationStartAlpha = customAttrs.getInteger(R.styleable.RippleView_fadeOutAnimationStartAlpha, FADE_OUT_ANIMATION_START_ALPHA);
    }

    public void setView(View view) {
        this.view = view;
    }

    private void setRadius(float radius) {
        this.radius = radius;
        view.invalidate();
    }

    private void setFadeOutAlpha(int alpha) {
        fadeOutAnimationColorPaint.setAlpha(alpha);
        view.invalidate();
    }

    public void onTouchEvent(MotionEvent event) {
        if (shouldStartRippleAnimation(event)) {
            touchedPointX = event.getX();
            touchedPointY = event.getY();

            if (rippleAnimationStarted) {
                rippleAnimationStarted = false;

                if (currentAnimation != null) {
                    currentAnimation.cancel();
                }
            }

            startRippleAnimation();
        }
    }

    private boolean shouldStartRippleAnimation(MotionEvent touchEvent) {
        return touchEvent.getAction() == MotionEvent.ACTION_DOWN &&
                isPreLollipopVersion();
    }

    private boolean isPreLollipopVersion() {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT;
    }

    private void startRippleAnimation() {
        currentAnimation = ObjectAnimator.ofFloat(this, "radius", 0.0f, getFinalRadiusForRippleEffect());
        currentAnimation.setDuration(rippleAnimationDuration);
        currentAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        currentAnimation.addListener(new RippleAnimationListener());
        currentAnimation.start();
    }

    private void startFadeOutAnimation() {
        ObjectAnimator animation = ObjectAnimator.ofInt(this, "fadeOutAlpha", fadeOutAnimationStartAlpha, 0);
        animation.setDuration(fadeOutAnimationDuration);
        animation.setInterpolator(new LinearInterpolator());
        animation.addListener(new FadeOutAnimationListener());
        animation.start();
    }

    private float getFinalRadiusForRippleEffect() {
        float distanceFromLeftEdgeOfView = touchedPointX;
        float distanceFromRightEdgeOfView = view.getMeasuredWidth() - touchedPointX;

        return Math.max(distanceFromLeftEdgeOfView, distanceFromRightEdgeOfView);
    }

    public void onDraw(Canvas canvas) {
        if (rippleAnimationStarted) {
            canvas.drawCircle(touchedPointX, touchedPointY, radius, rippleCirclePaint);
        }

        if (fadeOutAnimationStarted) {
            canvas.drawCircle(view.getMeasuredWidth()/2, view.getMeasuredHeight()/2, view.getMeasuredWidth(), fadeOutAnimationColorPaint);
        }
    }

    private class RippleAnimationListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
            rippleAnimationStarted = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            rippleAnimationStarted = false;
            if (!fadeOutAnimationStarted && !isRippleAnimationCanceled) {
                startFadeOutAnimation();
            }
            isRippleAnimationCanceled = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            rippleAnimationStarted = false;
            isRippleAnimationCanceled = true;
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    private class FadeOutAnimationListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
            fadeOutAnimationStarted = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            fadeOutAnimationStarted = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            fadeOutAnimationStarted = false;
        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
