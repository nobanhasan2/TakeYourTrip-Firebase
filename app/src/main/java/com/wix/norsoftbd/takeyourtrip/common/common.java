package com.wix.norsoftbd.takeyourtrip.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noban Hasan on 10/31/2017.
 */

public class common {

    public static String username;
    public  static String userIcon;
    public  static  String userType;
    public static String currentUser;

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        common.currentUser = currentUser;
    }



}
