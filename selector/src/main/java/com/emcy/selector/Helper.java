package com.emcy.selector;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by emilios on 29/04/2018.
 */

public class Helper {

    static void setTypeFace(Context context, TextView textView, String typeFacePath) {
        if (typeFacePath != null && !typeFacePath.isEmpty()) {
            try {
                Typeface type = Typeface.createFromAsset(context.getAssets(), typeFacePath);
                textView.setTypeface(type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
