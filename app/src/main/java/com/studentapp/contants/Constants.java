package com.studentapp.contants;

public class Constants {
    public static String device_id = "device_id";

    //Shared Preferences
    public static String MY_PREFS_NAME = "MyPrefsFile";

    public static String flag = "flag";
    public static String user_data = "user_data";
    public static String user_id = "user_id";

    //public static final int FLAG_MESSAGE = 2;
    public static final int FLAG_POLL = 2;
    public static final int FLAG_HOME = 3;
    public static final int FLAG_ACCOUNT = 4;

    //table name
    public static final String TBL_SCHOOLS = "schools";
    public static final String TBL_CITIES = "cities";
    public static final String TBL_USERS = "users";
    public static final String TBL_CLASS = "class";
    public static final String TBL_DIVISION = "division";
    public static final String TBL_POLLS = "polls";
    public static final String TBL_ANSWERED_POLLS = "answeredPolls";

    //users table fields
    public static final String firstName  = "firstName";
    public static final String lastName  = "lastName";
    public static final String mobile = "mobile";
    public static final String role = "role";
    public static final String gender  = "gender";
    public static final String studentClass  = "studentClass";
    public static final String section  = "section";
    public static final String division  = "division";
    public static final String options  = "options";
    public static final String question  = "question";
    public static final String standardClass  = "standardClass";
    public static final String student = "student";
    public static final String STUDENT_ID = "studentUserId";
    public static final String POLL_ID = "pollId";
    public static final String SCHOOL_ID = "schoolId";
    public static final String verified = "verified";
    public static final String password = "password";
    public static final String DATA_USERS = "userDetails";

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
