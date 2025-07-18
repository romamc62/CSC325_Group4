package com.bloodpressuremonitor.group4.csc325_group4.session;

//stores a Session object to hold the current session with user data
public class SessionManager {

    private static Session currSession;

    public static void setSession(Session session) {
        currSession = session;
    }

    public static Session getSession() {
        return currSession;
    }

    public static void closeSession() {
        currSession = null;
    }

}
