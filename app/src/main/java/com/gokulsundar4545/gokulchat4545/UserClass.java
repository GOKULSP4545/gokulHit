package com.gokulsundar4545.gokulchat4545;

import java.io.Serializable;

public class UserClass implements Serializable {

    String Username,Email,Password,ConformPassWord,PhoneNo2;

    public UserClass() {

    }

    public UserClass(String username, String email, String password, String conformPassWord, String phoneNo2) {
        this.Username = username;
        this.Email = email;
        this.Password = password;
        this.ConformPassWord = conformPassWord;
        this.PhoneNo2 = phoneNo2;
    }


    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getConformPassWord() {
        return ConformPassWord;
    }

    public void setConformPassWord(String conformPassWord) {
        this.ConformPassWord = conformPassWord;
    }

    public String getPhoneNo2() {
        return PhoneNo2;
    }

    public void setPhoneNo2(String phoneNo2) {
        this.PhoneNo2 = phoneNo2;
    }
}
