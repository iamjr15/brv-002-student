package com.studentapp.utils;

import android.app.Activity;
import android.content.Intent;


public class NavigatorManager {

    /**
     * start new activity without result code
     *
     * @param context
     * @param targetIntent
     */
    public static void startNewActivity(Activity context, Intent targetIntent) {
            context.startActivity(targetIntent);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * start new activity without result code
     *
     * @param context
     * @param targetIntent
     */
    public static void startNewActivityForImplicit(Activity context, Intent targetIntent) {
            context.startActivity(targetIntent);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    /**
     * start new activity with result code to return result
     *
     * @param context
     * @param targetIntent
     */
    public static void startNewActivityForResult(Activity context, Intent targetIntent, int requestCode) {
            context.startActivityForResult(targetIntent, requestCode);
            context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }




}