package com.bloodpressuremonitor.group4.csc325_group4.session;

public class SessionManager {
    private static Session session;

    public static void setSession(Session sess) {
        session = sess;
    }

    public static Session getSession() {
        return session;
    }

    public static void clearSession() {
        session = null;
    }
}
