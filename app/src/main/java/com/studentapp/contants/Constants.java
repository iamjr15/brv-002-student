package com.studentapp.contants;

public class Constants {
    public static String device_id = "device_id";

    public static String flag = "flag";
    public static String user_data = "user_data";
    public static String user_id = "user_id";

    public static final int FLAG_MESSAGE = 2;
    public static final int FLAG_HOME = 3;
    public static final int FLAG_ACCOUNT = 4;

    //table name
    public static final String TBL_SCHOOLS = "schools";
    public static final String TBL_CITIES = "cities";
    public static final String TBL_USERS = "users";

    //users table fields
    private static final String firstName  = "firstName";
    private static final String lastName  = "lastName";
    public static final String mobile = "mobile";
    public static final String role = "role";
    public static final String student = "student";
    public static final String verified = "verified";
    public static final String password = "password";

    /**
     * RESPONSE CODE
     */
    public static final int RESPONSE_CODE_404 = 404;
    public static final int RESPONSE_CODE_401 = 401;
    public static final int RESPONSE_CODE_200 = 200;
    public static final String SERVER_ERROR = "Something went wrong, please try again later.";
    public static final String INTERNET_ERROR = "No Internet Connection";
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_VALIDATION_ERROR = 3;
    public static final int STATE_LOADING = 4;
}
