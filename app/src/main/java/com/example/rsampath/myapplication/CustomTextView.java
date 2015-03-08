package com.example.rsampath.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by rsampath on 3/6/15.
 */
public class CustomTextView extends android.widget.TextView {

    public CustomTextView(Context context) {
        this(context, null);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();

    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle);
        init();

    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Android.ttf");
            setTypeface(tf);
        }
    }
}
