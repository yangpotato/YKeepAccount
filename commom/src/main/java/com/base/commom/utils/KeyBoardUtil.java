package com.base.commom.utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class KeyBoardUtil {
    public static void hideKeyBoard(Activity activity){
        InputMethodManager imm = (InputMethodManager)activity. getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }
}
