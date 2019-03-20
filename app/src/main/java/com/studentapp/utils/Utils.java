package com.studentapp.utils;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.signup.model.ModelUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class Utils {
    public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS = "0123456789";
    public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String TAG = "Utils";
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static String PREFERENCE_NAME = "GLOBAL_DATA";
    public static ProgressDialog pDialog = null;
    static Context ctx;


    public Utils(Context ctx) {
        super();
        Utils.ctx = ctx;
    }

    // -------------------------------------------------//
    // ----------------Show Toast-----------------------//
    // -------------------------------------------------//

    public static void makeToast(Context context, String text) {
        if (!isEmpty(text))
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    // -------------------------------------------------//
    // ----------------Show Toast-----------------------//
    // -------------------------------------------------//

    public static void showSnackBar(String text, View view) {

        //todo find text
        if (view != null) {
            Snackbar snackbar;
            snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorAccent));
//            TextView textView = snackBarView.findViewById(androidx..R.id.snackbar_text);
//            textView.setTextColor(ContextCompat.getColor(ctx, R.color.white));
            snackbar.show();
        }
    }
    // -------------------------------------------------//
    // ----------------Show Toast-----------------------//
    // -------------------------------------------------//

    public static void showSnackBarIndefinite(String text, View view) {
        Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE).show();
    }

    // -------------------------------------------------//
    // -----------------Share Preference---------------//
    // -------------------------------------------------//

    public static boolean putString(String key, String value) {
        SharedPreferences settings = ctx.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(String key) {
        return getString(ctx, key, "");
    }

    public static String getString(Context context, String key,
                                   String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    public static boolean putInt(String key, int value) {
        SharedPreferences settings = ctx.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getInt(String key) {
        return getInt(ctx, key, -1);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    public static boolean putLong(String key, long value) {
        SharedPreferences settings = ctx.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static long getLong(String key) {
        return getLong(ctx, key, -1);
    }

    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    public static boolean putFloat(String key, float value) {
        SharedPreferences settings = ctx.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static float getFloat(String key) {
        return getFloat(ctx, key, -1);
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    public static boolean putBoolean(String key, boolean value) {
        SharedPreferences settings = ctx.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(ctx, key, false);
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    // -------------------------------------------------//
    // --------email address validation-----------------//
    // -------------------------------------------------//
    public static boolean IsValidEmail(String email2) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email2);
        check = m.matches();

        return check;
    }

    // -------------------------------------------------//
    // --------showRandomInteger------------------------//
    // -------------------------------------------------//
    public static int showRandomInteger(int aStart, int aEnd, Random aRandom) {
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        // get the range, casting to long to avoid overflow problems
        long range = (long) aEnd - (long) aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long) (range * aRandom.nextDouble());
        int randomNumber = (int) (fraction + aStart);
        return randomNumber;
    }

    // -------------------------------------------------//
    // --------hide keyboard----------------------------//
    // -------------------------------------------------//
    public static void hideKeyBoard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager
                .hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void hideKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void ChangeKeyboardFocus(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    // -------------------------------------------------//
    // --------getStorageDirectory----------------------//
    // -------------------------------------------------//
    public static String getStorageDirectory() {
        Boolean isSDPresent = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);

        String filename;

        if (isSDPresent) {
            // yes SD-card is present

            filename = Environment.getExternalStorageDirectory().getPath();

        } else {
            filename = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES).getPath();

        }

        return filename;

    }

    // -------------------------------------------------//
    // --------getRealPathFromURI----------------------//
    // -------------------------------------------------//
    public static String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = ctx.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String GetDateOnRequireFormat(String date,
                                                String givenformat, String resultformat) {
        String result = "";
        SimpleDateFormat sdf;
        SimpleDateFormat sdf1;
        try {
            sdf = new SimpleDateFormat(givenformat, Locale.US);
            sdf1 = new SimpleDateFormat(resultformat, Locale.US);
            result = sdf1.format(sdf.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            sdf = null;
            sdf1 = null;
        }
        return result;
    }

    private static int getTimeDistanceInMinutesForTotalTime(long time, long now) {
        long timeDistance = now - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

    public static Date currentDate() {
       /* TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(Constants.DATE_FULL_FORMAT, Locale.US);
        simpleDateFormat.setTimeZone(timeZone);
        return calendar.getTime();*/
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.ENGLISH);
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

//Local time zone
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.ENGLISH);

//Time in GMT
     /*   try {
            return dateFormatLocal.parse(dateFormatGmt.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        //return new Date();
        return Calendar.getInstance().getTime();
    }

    public static boolean checkTimeBetweenTwoTimes(String strCTime, String fromTime, String toTime, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);

            Date time1 = dateFormat.parse(fromTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            Date time2 = dateFormat.parse(toTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            Date d = dateFormat.parse(strCTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    // -------------------------------------------------//
    // --------ChangeFocus of keyboard---------------//
    // -------------------------------------------------//

    public static boolean DateIsBeforeThanProvidedDate(String date_first, String date_second, String dateformat) {
        boolean is_before = false;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
            Date date1 = sdf.parse(date_first);
            Date date2 = sdf.parse(date_second);

            System.out.println(sdf.format(date1));
            System.out.println(sdf.format(date2));

            if (date1.compareTo(date2) > 0) {
                is_before = true;
            }
            LogUtils.Print("DateIsBeforeThanProvidedDate", "date1.compareTo(date2)..." + date1.compareTo(date2));


        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        LogUtils.Print("is_before", "is_before..." + is_before);
        LogUtils.Print("DateIsBeforeThanProvidedDate", "date_first..." + date_first);
        LogUtils.Print("DateIsBeforeThanProvidedDate", "date_second..." + date_second);

        return is_before;
    }

    public static boolean DateIsBeforeStrickThanProvidedDate(String date_first, String date_second, String dateformat) {
        boolean is_before = false;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
            Date date1 = sdf.parse(date_first);
            Date date2 = sdf.parse(date_second);

            System.out.println(sdf.format(date1));
            System.out.println(sdf.format(date2));

            if (date1.compareTo(date2) >= 0) {
                is_before = true;
            }


        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return is_before;
    }

    public static boolean DateIsBeforeThanOrEqualProvidedDate(String date_first, String date_second, String dateformat) {
        boolean is_before = false;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
            Date date1 = sdf.parse(date_first);
            Date date2 = sdf.parse(date_second);

            System.out.println(sdf.format(date1));
            System.out.println(sdf.format(date2));

            if (date1.compareTo(date2) <= 0) {
                is_before = true;
            }


        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return is_before;
    }

    public static boolean IsEqualDate(String date_first, String date_second, String dateformat) {
        boolean is_equal = false;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
            Date date1 = sdf.parse(date_first);
            Date date2 = sdf.parse(date_second);

            System.out.println(sdf.format(date1));
            System.out.println(sdf.format(date2));

            if (date1.compareTo(date2) == 0) {
                is_equal = true;
            }


        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return is_equal;
    }


    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftkeyboard(View view) {
        showSoftkeyboard(view, null);
    }

    // -------------------------------------------------//
    //---------------get current date------------------//
    // -------------------------------------------------//

    public static void showSoftkeyboard(View view, ResultReceiver resultReceiver) {
        Configuration config = view.getContext().getResources()
                .getConfiguration();
        if (config.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            InputMethodManager imm = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (resultReceiver != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT,
                        resultReceiver);
            } else {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }
    }

    // -------------------------------------------------//
    // --------------Compare Date----------------------//
    // -------------------------------------------------//

    // -------------------------------------------------//
    // --------------BLOCK SCREEN TOUCH-----------------//
    // -------------------------------------------------//
    public static void blockTouchableWindow(Window window) {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    // -------------------------------------------------//
    // --------------Compare Date----------------------//
    // -------------------------------------------------//

    // -------------------------------------------------//
    // --------------RELEASE SCREEN TOUCH-----------------//
    // -------------------------------------------------//
    public static void releaseWindowToTouch(Window window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    // -------------------------------------------------//
    // --------------Compare Date----------------------//
    // -------------------------------------------------//

    public static void startWebActivity(Context context, String url) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    // -------------------------------------------------//
    // --------------check is equal Date----------------------//
    // -------------------------------------------------//

    public static void startWebSearchActivity(Context context, String url) {
        final Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, url);
        context.startActivity(intent);
    }

    public static void startEmailActivity(Context context, int toResId,
                                          int subjectResId, int bodyResId) {
        startEmailActivity(context, context.getString(toResId),
                context.getString(subjectResId), context.getString(bodyResId));
    }

    // -------------------------------------------------//
    // --------------Hide Keyboard----------------------//
    // -------------------------------------------------//

    public static void startEmailActivity(Context context, String to,
                                          String subject, String body) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setType("message/rfc822");
        intent.setType("text/plain");
        if (!TextUtils.isEmpty(to)) {
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        }
        if (!TextUtils.isEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (!TextUtils.isEmpty(body)) {
            intent.putExtra(Intent.EXTRA_TEXT, body);
        }

        final PackageManager pm = context.getPackageManager();
        try {
            if (pm.queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY).size() == 0) {
                intent.setType("text/plain");
            }
        } catch (Exception e) {
            Log.w("Error.", e);
        }

        context.startActivity(intent);
    }

    // -------------------------------------------------//
    // --------------Show Keyboard----------------------//
    // -------------------------------------------------//

    public static String getAppName() {
        return getAppName(ctx, null);
    }

    public static String getAppName(Context context, String packageName) {
        String applicationName;

        if (packageName == null) {
            packageName = context.getPackageName();
        }

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, 0);
            applicationName = context
                    .getString(packageInfo.applicationInfo.labelRes);
        } catch (Exception e) {
            Log.w("error", "Failed to get version number." + e);
            applicationName = "";
        }

        return applicationName;
    }

    // -------------------------------------------------//
    // --------------get app VersionNumber--------------//
    // -------------------------------------------------//
    public static String getAppVersionNumber() {
        return getAppVersionNumber(ctx, null);
    }

    public static String getAppVersionNumber(Context context, String packageName) {
        String versionName;

        if (packageName == null) {
            packageName = context.getPackageName();
        }

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            // Log.e("Failed to get version number.",""+e);
            e.printStackTrace();
            versionName = "";
        }

        return versionName;
    }

    // -------------------------------------------------//
    // --------------Open WebBrowser--------------------//
    // -------------------------------------------------//

    public static String getAppVersionCode() {
        return getAppVersionCode(ctx, null);
    }

    // -------------------------------------------------//
    // --------------Open search in WebBrowser--------------------//
    // -------------------------------------------------//

    public static String getAppVersionCode(Context context, String packageName) {
        String versionCode;

        if (packageName == null) {
            packageName = context.getPackageName();
        }

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, 0);
            versionCode = Integer.toString(packageInfo.versionCode);
        } catch (Exception e) {
            Log.w("Failed ", e);
            versionCode = "";
        }

        return versionCode;
    }

    // -------------------------------------------------//
    // --------------Open Email Intent------------------//
    // -------------------------------------------------//

    // -------------------------------------------------//
    // --------------get SdkVersion---------------------//
    // -------------------------------------------------//
    public static int getSdkVersion() {
        try {
            return Build.VERSION.class.getField("SDK_INT").getInt(null);
        } catch (Exception e) {
            return 3;
        }
    }

    // -------------------------------------------------//
    // --------check is app run on emulator-------------//
    // -------------------------------------------------//
    public static boolean isEmulator() {
        return Build.MODEL.equals("sdk") || Build.MODEL.equals("google_sdk");
    }

    // -------------------------------------------------//
    // --------------get app Name-----------------------//
    // -------------------------------------------------//

    public static float dpToPx(float dp) {
        if (ctx == null) {
            return -1;
        }
        return dp * ctx.getResources().getDisplayMetrics().density;
    }

    // -------------------------------------------------//
    // --------convert pixcel to dp --------------------//
    // -------------------------------------------------//
    public static float pxToDp(float px) {
        if (ctx == null) {
            return -1;
        }
        return px / ctx.getResources().getDisplayMetrics().density;
    }

    // -------------------------------------------------//
    // --------convert dp to pixcel integer-------------//
    // -------------------------------------------------//
    public static float dpToPxInt(Context context, float dp) {
        return (int) (dpToPx(dp) + 0.5f);
    }

    // -------------------------------------------------//
    // --------convert pixcel to DpCeilInt--------------//
    // -------------------------------------------------//
    public static float pxToDpCeilInt(float px) {
        return (int) (pxToDp(px) + 0.5f);
    }

    // -------------------------------------------------//
    // --------------get app VersionCode----------------//
    // -------------------------------------------------//

    // -------------------------------------------------//
    // --------convert dp to px--------------//
    // -------------------------------------------------//
    public static int dpToPxInt(float dp) {
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String geFileFromAssets(String fileName) {
        if (ctx == null || isEmpty(fileName)) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(ctx.getResources()
                    .getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static String geFileFromRaw(int resId) {
        if (ctx == null) {
            return null;
        }

        StringBuilder s = new StringBuilder();
        try {
            InputStreamReader in = new InputStreamReader(ctx.getResources()
                    .openRawResource(resId));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                s.append(line);
            }
            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // -------------------------------------------------//
    // --------convert dp to pixcel---------------------//
    // -------------------------------------------------//

    public static List<String> geFileToListFromAssets(String fileName) {
        if (ctx == null || isEmpty(fileName)) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        try {
            InputStreamReader in = new InputStreamReader(ctx.getResources()
                    .getAssets().open(fileName));
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
            br.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // -------------------------------------------------//
    // --------get list of file from raw--------------//
    // -------------------------------------------------//
    public static List<String> geFileToListFromRaw(int resId) {
        if (ctx == null) {
            return null;
        }

        List<String> fileContent = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            InputStreamReader in = new InputStreamReader(ctx.getResources()
                    .openRawResource(resId));
            reader = new BufferedReader(in);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRandomNumbersAndLetters(int length) {
        return getRandom(NUMBERS_AND_LETTERS, length);
    }

    // -------------------------------------------------//
    // --------------get random number only-------------//
    // -------------------------------------------------//
    public static String getRandomNumbers(int length) {
        return getRandom(NUMBERS, length);
    }

    // -------------------------------------------------//
    // --------geFileFromAssets-------------------------//
    // -------------------------------------------------//

    // -------------------------------------------------//
    // --------------get random letter only-------------//
    // -------------------------------------------------//
    public static String getRandomLetters(int length) {
        return getRandom(LETTERS, length);
    }

    public static String getRandomCapitalLetters(int length) {
        return getRandom(CAPITAL_LETTERS, length);
    }

    public static String getRandomLowerCaseLetters(int length) {
        return getRandom(LOWER_CASE_LETTERS, length);
    }

    // -------------------------------------------------//
    // --------geFileFromRaw-------------------------//
    // -------------------------------------------------//

    public static String getRandom(String source, int length) {
        return isEmpty(source) ? null : getRandom(source.toCharArray(), length);
    }

    // -------------------------------------------------//
    // --------get list of file from asset--------------//
    // -------------------------------------------------//

    public static String getRandom(char[] sourceChar, int length) {
        if (sourceChar == null || sourceChar.length == 0 || length < 0) {
            return null;
        }

        StringBuilder str = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(sourceChar[random.nextInt(sourceChar.length)]);
        }
        return str.toString();
    }

    public static int getRandom(int max) {
        return getRandom(0, max);
    }

    // -------------------------------------------------//
    // ---get random number and letter combination------//
    // -------------------------------------------------//

    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    public static boolean shuffle(Object[] objArray) {
        if (objArray == null) {
            return false;
        }

        return shuffle(objArray, getRandom(objArray.length));
    }

    public static boolean shuffle(Object[] objArray, int shuffleCount) {
        int length;
        if (objArray == null || shuffleCount < 0
                || (length = objArray.length) < shuffleCount) {
            return false;
        }

        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            Object temp = objArray[length - i];
            objArray[length - i] = objArray[random];
            objArray[random] = temp;
        }
        return true;
    }

    // -------------------------------------------------//
    // --------------get random capital letter----------//
    // -------------------------------------------------//

    public static int[] shuffle(int[] intArray) {
        if (intArray == null) {
            return null;
        }

        return shuffle(intArray, getRandom(intArray.length));
    }

    // -------------------------------------------------//
    // --------------get random lowecase letter----------//
    // -------------------------------------------------//

    public static int[] shuffle(int[] intArray, int shuffleCount) {
        int length;
        if (intArray == null || shuffleCount < 0
                || (length = intArray.length) < shuffleCount) {
            return null;
        }

        int[] out = new int[shuffleCount];
        for (int i = 1; i <= shuffleCount; i++) {
            int random = getRandom(length - i);
            out[i - 1] = intArray[random];
            int temp = intArray[length - i];
            intArray[length - i] = intArray[random];
            intArray[random] = temp;
        }
        return out;
    }

    public static String MD5(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return new String(encodeHex(messageDigest.digest()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static char[] encodeHex(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }

    public static boolean isApplicationInBackground() {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(ctx.getPackageName())) {
                        //If your app is the process in foreground, then it's not in running in background
                        return false;
                    }
                }
            }
        }


        return true;
    }

    public static int getHeightAsPerRation(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        LogUtils.Print("width", " ==> " + width);
        LogUtils.Print("height", " ==> " + height);
        LogUtils.Print("return height", " ==> " + height * 310 / 1440);


        return ((int) pxToDp((float) (height * 310) / 1440));
    }

    // -------------------------------------------------//
    // -----------Round Double Value--------------------//
    // -------------------------------------------------//
    public static String round(double value, int places) {
        double a = value;
        BigDecimal bd = new BigDecimal(a);
        BigDecimal res = bd.setScale(places, RoundingMode.DOWN);
        System.out.println("" + res.toPlainString());
        return res.toPlainString();
    }

    // -------------------------------------------------//
    // -----------DoubleToInt--------------------//
    // -------------------------------------------------//
    public static String DoubleToInt(String value) {
        double a = Double.valueOf(value);
        BigDecimal bd = new BigDecimal(a);

        System.out.println("" + bd.intValue());
        return "" + bd.intValue();
    }

    // -------------------------------------------------//
    // -----------GetPriceInFormat--------------------//
    // -------------------------------------------------//
    public static String ConvertPriceInFormat(String value) {
        double a = Double.valueOf(value);
        BigDecimal bd = new BigDecimal(a);

        System.out.println("" + bd.intValue());
        return "$" + bd.intValue();
    }

    // -------------------------------------------------//
    // -----------Show Custome Dialog-------------------//
    // -------------------------------------------------//
    public static void ShowAlert(Context ctx, String msg, String title) {
        try {


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    ctx);

            // set title
            alertDialogBuilder.setTitle("" + title);

            alertDialogBuilder.setIcon(R.mipmap.ic_launcher_round);

            // set dialog message
            alertDialogBuilder
                    .setMessage("" + msg)
                    .setCancelable(false)
                    .setPositiveButton(ctx.getResources().getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------//
    // --------------encode string with MD5-------------//
    // -------------------------------------------------//

    public static String GetDeviceID() {
        // TODO Auto-generated method stub
        String id = Settings.Secure.getString(ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (getString(Constants.device_id).equals(""))
            putString(Constants.device_id, id);
        LogUtils.Print(TAG, "GetDeviceID:-" + id);
        return id;

    }

    public static void clearAllPreferences() {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
//        App.USER_DETAILS = null;
//        stopBackgroundServices(ctx);
//        App.getDataBaseInstance().ClearDatabase();
    }

    // -------------------------------------------------//
    // -----------isApplicationInBackground-------------//
    // -------------------------------------------------//

    // -------------------------------------------------//
    // -----------GetDistance------------//
    // -------------------------------------------------//
    public static double GetDistance(double lat1, double lon1, double lat2,
                                     double lon2) {
        // TODO Auto-generated method stub
        android.location.Location locationA = new android.location.Location("a");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lon1);
        android.location.Location LocationB = new android.location.Location("b");
        LocationB.setLatitude(lat2);
        LocationB.setLongitude(lon2);
        // return in mi
        // return locationA.distanceTo(LocationB) * 0.000621371;
        // ---------------------
        // return in mitter
        return locationA.distanceTo(LocationB);
    }

    // -------------------------------------------------//
    // -----------get Height as per ration -------------//
    // -------------------------------------------------//

    public static double RoundDouble(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static boolean ISappInstalledOrNot(String uri) {
        PackageManager pm = ctx.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    // -------------------------------------------------//
    // -----------GetCurrentTimeStamp---------------------//
    // -------------------------------------------------//
    public static String GetCurrentTimeStamp() {
        Long tsLong = System.currentTimeMillis();
        return tsLong.toString();
    }

    public static long GetCurrentTimeStampInLong() {
        return System.currentTimeMillis();
    }


    // -------------------------------------------------//
    // -----------Get Millisecond from string date----------------//
    // -------------------------------------------------//
    public static Date getOneDayMinusFromStringDate(String strDate, String format) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, 15);
        cal.add(Calendar.DATE, -1);
        LogUtils.Print(TAG, "One day --> " + cal.getTime());
        return cal.getTime();
    }

    // -------------------------------------------------//
    // -----------Get Millisecond from string date----------------//
    // -------------------------------------------------//
    public static Date getHalfTimeOfTwoStringDates(String strDate, String format) {
        Date date = getDateFromString(strDate, format);

        String strDateCurrent = GetDateFromTimeStamp(GetCurrentTimeStamp(), format);
        Date dateCurrent = getDateFromString(strDateCurrent, format);

        long diff = (date.getTime() + dateCurrent.getTime()) / 2;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(diff);
        LogUtils.Print(TAG, "Half day --> " + cal.getTime());
        return cal.getTime();
    }

    public static Date getDateFromString(String strDate, String format) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDateFormat(String source, String sourceFormat, String destFormate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(sourceFormat);
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat targetFormat = new SimpleDateFormat(destFormate);
        String targetdatevalue = targetFormat.format(sourceDate);
        return targetdatevalue;
    }


    // -------------------------------------------------//
    // --------removeLastChar----------------------------//
    // -------------------------------------------------//
    public static String removeLastChar(String str, char sign) {
        if (!str.equalsIgnoreCase("") && str.length() > 0 && str.charAt(str.length() - 1) == sign) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static String removeLastChar(String str) {
        return removeLastChar(str, ',');
    }

    public static String GetDefaultTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        try {
            return "" + tz.getID();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }


    // -------------------------------------------------//
    // -----------appInstalledOrNot---------------------//
    // -------------------------------------------------//

    public static String GetCurrentNanoTime() {
        Long tsLong = System.nanoTime();
        String ts = tsLong.toString();
        return ts;
    }

    public static String GetDateFromTimeStamp(String milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        // change format to UTC Time Zone.
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(milliSeconds));
        return formatter.format(calendar.getTime());
    }

    public static String addDayInDate(String dt, String dateFormat, int num) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);
        return sdf.format(c.getTime());
    }

    public static double roundDouble(double Rval, int numberOfDigitsAfterDecimal) {
        double p = (float) Math.pow(10, numberOfDigitsAfterDecimal);
        Rval = Rval * p;
        double tmp = Math.floor(Rval);
        System.out.println("~~~~~~tmp~~~~~" + tmp);
        return tmp / p;
    }

    public static void StartRefreshing(SwipeRefreshLayout mSwipeContainer) {

        if (mSwipeContainer != null) {
            mSwipeContainer.setRefreshing(true);

        }
    }

    public static void StopRefreshing(SwipeRefreshLayout mSwipeContainer) {
        if (mSwipeContainer != null) {
            if (mSwipeContainer.isRefreshing()) {
                mSwipeContainer.setRefreshing(false);
            }
        }
    }

    public static void startGoogleMapNavigationActivity(Context context, double source_lat, double source_lng, double dest_lat, double dest_lng) {
        double lat2, lng2;


        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?" + "saddr=" + source_lat
                        + "," + source_lng + "&daddr=" + dest_lat + "," + dest_lng));
        intent.setClassName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");
        context.startActivity(intent);

    }

    public static void startVideoPlayerActivity(Context context, String path) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(path), "video/mp4");
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static boolean checkPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;


    }

    /**
     * show Permissions Dialog
     *
     * @param activity
     * @param permissions
     * @param REQUEST_CODE
     */
    public static void showPermissionsDialog(Activity activity, String[] permissions, int REQUEST_CODE) {
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE);
    }

    // -------------------------------------------------//
    // -----------GetDefaultTimeZone---------------------//
    // -------------------------------------------------//

    /**
     * Make Native Call
     *
     * @param activity
     */
    public static void makeNativeCall(Activity activity, String mobileNo) {
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobileNo));
//        activity.startActivity(intent);
    }

    // -------------------------------------------------//
    // -----------GetCurrentNanoTime---------------------//
    // -------------------------------------------------//

    /**
     * copy database
     *
     * @param dbis
     * @param dbPath
     */
    public static void copyDataBase(InputStream dbis, String dbPath) {
        try {
            // open your local database as the input stream
            OutputStream dbos = new FileOutputStream(dbPath);
            // path to the just created empty database
            byte[] buffer = new byte[1024];
            while (dbis.read(buffer) > 0) {
                dbos.write(buffer);
            }
            dbis.close();
            dbos.flush();
            dbos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------//
    // ------------ GetDateFromTimeStamp----------------------//
    // -------------------------------------------------//

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    // -------------------------------------------------//
    // ------------ GetDateFromTimeStamp----------------------//
    // -------------------------------------------------//


    // -------------------------------------------------//
    // --------- Navigate user to login screen ---------//
    // -------------------------------------------------//
    public static void navigateUserToLoginScreen(String strMsg, Activity activity) {
        Utils.makeToast(activity, strMsg);


//        Utils.clearAllPreferences();


//        Intent intent = new Intent(app_namectivity, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        NavigatorManager.startNewActivity(activity, intent);
    }


// -------------------------------------------------//
    //---------Open gogole map navigation activity---------//
    // -------------------------------------------------//

    public static int getFileSizeInMB(String filepath) {
        try {
            File file = new File(filepath);
            Long fileSizeInBytes = file.length();
            float fileSizeInKB = fileSizeInBytes / 1024;
            // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
            float fileSizeInMB = fileSizeInKB / 1024;

            return Math.round(fileSizeInMB);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // -------------------------------------------------//
    //---------Open video player activity---------//
    // -------------------------------------------------//

    public static void sharePostViaIntent(Context context, String textToShare, String imgURL) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, textToShare);
       /* if(!imgURL.equals("")) {
            Uri imageUri = Uri.parse(imgURL);
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        }*/
        intent.setType("text/plain");
        context.startActivity(Intent.createChooser(intent, "Share via..."));
    }


    // -------------------------------------------------//
    //---------Copy text to clipboard---------------//
    // -------------------------------------------------//

    // -------------------------------------------------//
    // ---------Replace string at last occurance---------//
    // -------------------------------------------------//
    public static String replaceLast(String string, String toReplace, String replacement) {
        int pos = string.lastIndexOf(toReplace);
        if (pos > -1) {
            return string.substring(0, pos)
                    + replacement
                    + string.substring(pos + toReplace.length());
        } else {
            return string;
        }
    }

    // -------------------------------------------------//
    // ---------Replace string at next occurance---------//
    // -------------------------------------------------//
    public static String replaceNext(String string, String toReplace, String replacement, int startIndex) {
        int pos = string.indexOf(toReplace, startIndex);
        if (pos > -1) {
            return string.substring(0, pos)
                    + replacement
                    + string.substring(pos + toReplace.length());
        } else {
            return string;
        }
    }

    // ---------------------------------------------------------------------//
    // -----calculate no of columns for grid layout in recyclerview---------//
    // -------------------------------------------------------------------//
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 120);
    }

    // ---------------------------------------------------------------------//
    // ----- Generate random color---------//
    // -------------------------------------------------------------------//
    public static int generateRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static int generateRandomColor(int intAlpha) {
        Random rnd = new Random();
        return Color.argb(intAlpha, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static String splitString(String s) {
        String[] separated = s.split("/");
        String newString = separated[separated.length - 1];
        return newString;
    }


    // -------------------------------------------------//
    // --------internet coonection states---------------//
    // -------------------------------------------------//
    public static boolean IsInternetOn() {

        boolean connected = false;
        final ConnectivityManager cm = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            connected = true;
        } else if (netInfo != null && netInfo.isConnected()
                && cm.getActiveNetworkInfo().isAvailable()) {
            connected = true;
        } else if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL("http://www.google.com");
                HttpURLConnection urlc = (HttpURLConnection) url
                        .openConnection();
                urlc.setConnectTimeout(3000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    connected = true;
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (cm != null) {
           /* final NetworkInfo[] netInfoAll = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfoAll) {
                System.out.println("get network type :::" + ni.getTypeName());
                if ((ni.getTypeName().equalsIgnoreCase("WIFI") || ni
                        .getTypeName().equalsIgnoreCase("MOBILE"))
                        && ni.isConnected() && ni.isAvailable()) {
                    connected = true;
                    if (connected) {
                        break;
                    }
                }
            }*/
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected() && activeNetwork.isAvailable()) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    connected = true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    connected = true;
                }
            } else {
                // not connected to the internet
                connected = false;
            }
        }
        return connected;

    }

    // ---------------------------------------------------//
    // -------------- get Screen height ----------------//
    // -------------------------------------------------//
    public static int getScreenWidth(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    // ---------------------------------------------------//
    // -------------- generate params ----------------//
    // -------------------------------------------------//
    public static Map<String, String> generateParams(Map<String, String> params) {
        return params;
    }

    // ---------------------------------------------------//
    // -------------- generate params ----------------//
    // -------------------------------------------------//
//    public static RequestParams generateRequestParams(RequestParams params) {
//        return params;
//    }


    // -------------------------------------------------//
    // -----get size in MB of image from path-----------//
    // -------------------------------------------------//

    // -------------------------------------------------//
    // --------hide keyboard----------------------------//
    // -------------------------------------------------//
    public static void hideKeyBoardFromEditText(Context context, List<EditText> editText) {
        for (int i = 0; i < editText.size(); i++) {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(inputMethodManager)
                        .hideSoftInputFromWindow(editText.get(i).getWindowToken(), 0);
            }
        }

    }

    // -------------------------------------------------//
    // --------------Share Intent-----------------------//
    // -------------------------------------------------//

    // -------------------------------------------------//
    // -----------IS GPD SETTING ON---------------------//
    // -------------------------------------------------//
//    public static boolean isGPSRequire() {
//        return getString(Constants.IS_GPS_REQUIRE).equals(Constants.IS_GPS_REQUIRE_YES);
//    }


    // -------------------------------------------------//
    // --------set color to swipe refresh layout-------//
    // -------------------------------------------------//
    public static void setColorToSwipeRefreshLayout(SwipeRefreshLayout srl) {
        try {
            srl.setColorSchemeResources(R.color.colorPrimary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissProgressDialog() {

        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    public static String formatDoubleValue(double d) {
        if (d == (long) d)
            return String.format(Locale.ENGLISH, "%d", (long) d);
        else
            return String.format("%s", d);
    }

    // -------------------------------------------------//
    // --------check percentage is greater then 100----//
    // -------------------------------------------------//
    public static boolean IsInvalidPercentage(String text) {

        boolean result = false;
        try {
            if (text != null && !text.isEmpty()) {
                int value = Integer.parseInt(text);
                if (value > 100) {
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * save logged in user data
     */
    public static void saveUserData(String userId, ModelUser user) {
        putString(Constants.user_data, new Gson().toJson(user));
        putString(Constants.user_id, userId);
    }

    /**
     * get logged in user data
     */
    public static ModelUser getUserData() {
        if (!getString(Constants.user_data).isEmpty()) {
            return new GsonBuilder().create().fromJson(getString(Constants.user_data), ModelUser.class);
        } else
            return null;
    }

    /**
     * get logged user id
     */
    public static String getUserId() {
        return getString(Constants.user_id);
    }

    // -------------------------------------------------//
    // --------IS GPS on states---------------//
    // -------------------------------------------------//
    public boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) ctx
                .getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // -------------------------------------------------//
    // --------phone number validation-----------------//
    // -------------------------------------------------//
    public boolean IsValidPhoneNumber(String phone) {
        boolean check;
        Pattern p;
        Matcher m;
        // String PHONE_STRING = "^[+]?[0-9]{10,13}$";
        String PHONE_STRING = "^\\+(?:[0-9]●?){6,14}[0-9]$";

        p = Pattern.compile(PHONE_STRING);

        m = p.matcher(phone);
        check = m.matches();


        return check;
    }

    // -------------------------------------------------//
    // --------password >6 char validation--------------//
    // -------------------------------------------------//
    @SuppressWarnings("unused")
    private boolean IsValidPassword(String pass) {
        return pass != null && pass.length() > 6;
    }

    // -------------------------------------------------//
    // --------min no of char validation--------------//
    // -------------------------------------------------//
    @SuppressWarnings("unused")
    private boolean IsMinChar(String text, int min) {
        return text != null && text.length() >= min;
    }

    // -------------------------------------------------//
    // --------max no of char validation--------------//
    // -------------------------------------------------//
    @SuppressWarnings("unused")
    private boolean IsMaxChar(String text, int max) {
        return text != null && text.length() <= max;
    }

    // -------------------------------------------------//
    // --------------get account UNIT--------------------//
    // -------------------------------------------------//

    // -------------------------------------------------//
    // --------edittext empty or not validation---------//
    // -------------------------------------------------//
    @SuppressWarnings("unused")
    private boolean IsEmpty(EditText et) {
        return et.getText().toString().equalsIgnoreCase("");
    }

    // -------------------------------------------------//
    // --------getRealPathFromURI----------------------//
    // -------------------------------------------------//
    @SuppressWarnings("deprecation")
    public String getRealPathFromURI(Uri contentUri, Activity activity) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.managedQuery(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = ctx.getContentResolver().query(contentUri, null, null,
                null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

}
