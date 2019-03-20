package com.studentapp.utils;

import android.util.Log;

public class LogUtils {
    public static boolean IS_Debug_mode = true;
    public static boolean IS_DB_Debug_mode = true;

    public static void Print(String tag, String text) {

        if (IS_Debug_mode)
            Log.e(tag, "==========" + text);
    }

    public static void DBPrint(String tag, String text) {

        if (IS_DB_Debug_mode)
            Log.e(tag, "==========" + text);
    }
}
