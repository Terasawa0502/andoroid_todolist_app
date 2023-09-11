package com.example.todolistapp.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {

    /**
     * キーボード表示用メソッド
     * @param context
     * @param view
     */

    public static void showSoftKeyboard(Context context, View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = context.getSystemService(InputMethodManager.class);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * キーボード非表示用メソッド
     * @param context
     * @param view
     */
    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = context.getSystemService(InputMethodManager.class);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
