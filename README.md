RippleEffect
================

Basic implementation of Android L ripple effect.

### Integration
The lib is available on Maven Central, you can find it with [Gradle, please](http://gradleplease.appspot.com/#rippleeffect)

``` xml

dependencies {
}

```

### Usage

#### RippleButton/RippleRadioButton

1. Declare buttons / radio buttons if one of the following classes.

``` xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:ripple="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

  <com.kremski.android.material.RippleButton
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#0053BF"
        ripple:fadeOutAnimationColor="@color/add_control_fade_out_animation_color"
        ripple:fadeOutAnimationDuration="@integer/fast_fade_out_animation_duration"
        ripple:fadeOutAnimationStartAlpha="@integer/fade_out_animation_start_alpha"
        ripple:rippleAnimationColor="@color/add_control_ripple_animation_color"
        ripple:rippleAnimationDuration="@integer/fast_ripple_animation_duration" />

    <com.kremski.android.material.RippleButton
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#0053BF"
        ripple:fadeOutAnimationColor="@color/add_control_fade_out_animation_color"
        ripple:fadeOutAnimationDuration="@integer/fast_fade_out_animation_duration"
        ripple:fadeOutAnimationStartAlpha="@integer/fade_out_animation_start_alpha"
        ripple:rippleAnimationColor="@color/add_control_ripple_animation_color"
        ripple:rippleAnimationDuration="@integer/fast_ripple_animation_duration/>

</LinearLayout>
```

2. If you want to use this effect in your own view, use RippleEffect.

``` java
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
```

### Customization

You can change several attributes in the XML file;

* ripple:fadeOutAnimationColor [color def:#FFFFFF] --> Starting color of fade out animation (after ripple animation)
* ripple:fadeOutAnimationDuration [integer def:500 ] --> Duration (in ms) of fade out animation (after ripple animation) 
* ripple:fadeOutAnimationStartAlpha [integer def:204 0-255] --> Starting alpha of fade out animation 
* ripple:rippleAnimationColor [color def:#FFFFFF] --> Color of ripple animation 
* ripple:rippleAnimationDuration [integer def:300 0-255] --> Duration of ripple animation

### MIT License

```
    The MIT License (MIT)

    Copyright (c) 2014 Robin Chutaux

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
```
