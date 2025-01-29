package controllers;

public class AccountInfo {
    private static int currentUser = -1;
    private static String currentUsername = "";
    private static int currentArea = -1;

    public static int getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(int currentUser) {
        AccountInfo.currentUser = currentUser;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void setCurrentUsername(String currentUsername) {
        AccountInfo.currentUsername = currentUsername;
    }

    public static int getCurrentArea() {
        return currentArea;
    }

    public static void setCurrentArea(int currentArea) {
        AccountInfo.currentArea = currentArea;
    }
}
