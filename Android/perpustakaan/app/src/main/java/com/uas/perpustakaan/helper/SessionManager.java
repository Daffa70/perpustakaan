package com.uas.perpustakaan.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences preferences;
    public static final String KEY_USERID = "user_id";
    public static final String KEY_USERIDAMIN = "user_idadmin";

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences("prefSessionManager",
                Context.MODE_PRIVATE);
    }

    public void setUserId(String userId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USERID, userId);
        editor.apply();
    }

    public void clearEditor() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public boolean isLoggedIn() {
        if (preferences.getString(KEY_USERID, null) == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public void setUserIdAdmin(String userIdAdmin) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USERIDAMIN, userIdAdmin);
        editor.apply();
    }

    public void clearEditorAdmin() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public boolean isLoggedInAdmin() {
        if (preferences.getString(KEY_USERIDAMIN, null) == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public String getUserIdAdmin() {
        return preferences.getString(KEY_USERIDAMIN, null);
    }

    public String getUserId() {
        return preferences.getString(KEY_USERID, null);
    }
}
