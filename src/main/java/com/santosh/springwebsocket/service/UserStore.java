package com.santosh.springwebsocket.service;

import com.santosh.springwebsocket.dto.User;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class UserStore {

    private List<User> users = new ArrayList<>();

    private static UserStore instance;

    private UserStore() {
    }

    public static synchronized UserStore getInstance() {
        if (instance == null) {
            instance = new UserStore();
        }
        return instance;
    }

    public void registerUser(User user) {
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++");

        boolean alreadyRegistered = false;

        for (User userObj : users) {
            if (userObj.getMobileNumber().equalsIgnoreCase(user.getMobileNumber()) && userObj.getChannel().equalsIgnoreCase(user.getChannel())) {
                alreadyRegistered = true;
            }
        }

        log.info(user + " is added to user store");

        if (!alreadyRegistered) {
            users.add(user);
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void unregisterUser(User user) {
        List<User> updatedUserList = new ArrayList<>();
        for (User userObj : users) {
            if (!userObj.getSessionId().equalsIgnoreCase(user.getSessionId())) {
                updatedUserList.add(userObj);
            } else {
                log.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                log.info(user + " is removed from user store");
            }
        }
        users = updatedUserList;
    }

    public synchronized User getUser(String userId) {
        Map<String, User> map = new HashMap<>();

        for (User user : users) {
            map.put(user.getUserId(), user);
        }

        return map.get(userId);
    }
}
