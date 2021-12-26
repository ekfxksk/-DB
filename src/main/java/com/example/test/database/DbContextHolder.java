package com.example.test.database;

public class DbContextHolder {
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setDBUser(String dbUser) {
        DbContextHolder.threadLocal.set(dbUser);
    }

    public static String getDBUser() {
        return threadLocal.get();
    }
}
