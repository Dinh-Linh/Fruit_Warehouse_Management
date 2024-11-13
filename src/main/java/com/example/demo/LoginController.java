package com.example.demo;

import com.example.demo.entity.TaiKhoan;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginController {
    private static List<TaiKhoan> taiKhoanList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Tạo một số tài khoản giả định với dữ liệu ngẫu nhiên
        taiKhoanList.add(new TaiKhoan("user1", "password123", "ON", Time.valueOf("00:00:00"), Date.valueOf("2024-01-01"), Date.valueOf("2025-01-01")));
        taiKhoanList.add(new TaiKhoan("user2", "pass456", "OFF", Time.valueOf("00:00:00"), Date.valueOf("2024-02-01"), Date.valueOf("2025-02-01")));
        taiKhoanList.add(new TaiKhoan("user3", "myPassword789", "LOCK", Time.valueOf("00:00:00"), Date.valueOf("2024-03-01"), Date.valueOf("2025-03-01")));
        taiKhoanList.add(new TaiKhoan("user4", "securePass", "FIRST", Time.valueOf("00:00:00"), Date.valueOf("2024-04-01"), Date.valueOf("2025-04-01")));
        taiKhoanList.add(new TaiKhoan("user5", "abc@123", "QUIT", Time.valueOf("00:00:00"), Date.valueOf("2024-05-01"), Date.valueOf("2025-05-01")));
        System.out.println("Nhập vào username và password\n");
        String username = sc.nextLine();
        String password = sc.nextLine();
        TaiKhoan tk = TaiKhoan.findAccount(taiKhoanList, username);
        if (tk != null) {
            tk.checkLogin(username, password);
        } else {
            System.out.println("Không tồn tại tài khoản");
        }
    }
}
