package com.example.demo.utils;

public class Validator {
    public boolean isValidNumber(String input){
        //Chỉ nhập số từ 1-9. Nếu có 1 số thì không được là số 0
        return input.matches("^[1-9]$|^[0-9]{2,}$");
    }
}
