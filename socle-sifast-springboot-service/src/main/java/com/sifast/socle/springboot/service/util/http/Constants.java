package com.sifast.socle.springboot.service.util.http;

public final class Constants {

    public static final int STATUS_OK = 200;

    public static final int STATUS_CREATED = 201;

    public static final int STATUS_ACCEPTED = 202;

    public static final int STATUS_SERVER_ERROR = 500;

    public static final int STATUS_NOT_FOUND = 404;

    public static final int STATUS_BAD_REQUEST = 400;

    public static final String CHECK_URL = "Check your URL";

    public static final String CHECK_ENTRY = "Error occured, Check your entry please.";

    public static final String NO_RESPONSE = "No response data for this request.";

    public static final String ERROR_LEVEL_MESSAGE = "ERROR -";

    // At least one Upper case, one lower case, one number and 6 characters long
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
    
    public static final String PASSWORD_ERROR_MESSAGE = "Password must have at least one upper case, one lower case, one number and 6 characters long";

    public static final String EMAIL_REGEX ="^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
    
    public static final String NOT_BLANK_REGEX= "^(?=\\s*\\S).*$";

    private Constants() {
    }
}
