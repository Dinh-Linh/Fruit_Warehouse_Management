package com.example.demo.utils;

import com.example.demo.entity.TaiKhoan;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    private List<TaiKhoan> taiKhoanList = new ArrayList<>();

    public boolean isValidNumber(String input){
        //Chỉ nhập số từ 1-9. Nếu có 1 số thì không được là số 0
        return input.matches("^[1-9]$|^[0-9]{2,}$");
    }

    public boolean checkPassword(String input){
        //Password đảm bảo chứa ít nhất 1 chữ thường, 1 chữ hoa, 1 kí tự đặc biệt, 1 số, min = 12 kí tự
        if(input.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$")){
            return true;
        }
        else {
            System.out.println("Password is incorrect");
            return false;
        }
    }

    public boolean checkUsername(String input){
        if(input.length() >= 10 && input.length() <= 40){
            return true;
        }
        else return false;
    }

}
