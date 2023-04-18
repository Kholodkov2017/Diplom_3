package helpers;

import java.util.*;

public class Constants {
    public static final int DEFAULT_WAITING = 15;
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static String CONTENT_TYPE_HEADER = "Content-Type";
    public static String CONTENT_TYPE_HEADER_VALUE = "application/json";
    public static final String REGISTER_PTH = "/api/auth/register";
    public static final String LOGIN_PTH = "/api/auth/login";
    public static final String USER_PTH = "/api/auth/user";

    public static final String FRONT_REG_PAGE = "/register";
    public static final String FRONT_RESTORE_PAGE = "/forgot-password";
    public static final String FRONT_LOGIN_PAGE = "/login";

    public static final List< String> INGREDIENT_TYPES_MAP = Arrays.asList(
            "Булки",
            "Соусы",
            "Начинки");
}