package com.honeacademy.petfinder.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by jmaina on 8/31/17.
 */

public class PetFinderTextView extends android.support.v7.widget.AppCompatTextView {

    public PetFinderTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PetFinderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PetFinderTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
            setTypeface(tf);
        }
    }
}
