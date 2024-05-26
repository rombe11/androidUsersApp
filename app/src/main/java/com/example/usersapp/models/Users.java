package com.example.usersapp.models;

import java.util.List;

public class Users {
    private List<RandomUser> userList;

    public Users(List<RandomUser> userList) {
        this.userList = userList;
    }

    public List<RandomUser> getUserList() {
        return userList;
    }

    public void setUserList(List<RandomUser> userList) {
        this.userList = userList;
    }

    public boolean containsUser(RandomUser user) {
        for (RandomUser existingUser : userList) {
            if (existingUser.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void addUser(RandomUser user) {
        if (!containsUser(user)) {
            userList.add(user);
        }
    }
}