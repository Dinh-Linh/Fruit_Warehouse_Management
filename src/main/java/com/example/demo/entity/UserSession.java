package com.example.demo.entity;

import entity.Taikhoan;

public class UserSession {
    private static Taikhoan currentAccount;

    public static Taikhoan getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Taikhoan account) {
        currentAccount = account;
    }
}

