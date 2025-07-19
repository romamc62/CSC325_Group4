package com.bloodpressuremonitor.group4.csc325_group4.session;

import com.google.firebase.auth.UserRecord;

public class Session {
    private final UserRecord user;

    public Session(UserRecord user) {
        this.user = user;
    }

    public UserRecord getUser() {
        return user;
    }
}
