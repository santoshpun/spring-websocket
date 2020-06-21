package com.santosh.springwebsocket.controller;

import com.santosh.springwebsocket.dto.BaseResponse;
import com.santosh.springwebsocket.dto.SendMessageRequest;
import com.santosh.springwebsocket.dto.User;
import com.santosh.springwebsocket.service.UserStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("user/session")
public class UserSessionController {

    @PostConstruct
    public void init() {
        ScheduledExecutorService statusTimerExecutor = Executors.newSingleThreadScheduledExecutor();

        statusTimerExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    User user = UserStore.getInstance().getUser("santoshweb");
                    if (user != null) {
                        user.getWebSocketSession().sendMessage(new TextMessage("test " + new Date()));
                    }
                } catch (Exception e) {
                    log.error("Exception ", e);
                }
            }
        }, 5000, 5000, TimeUnit.MILLISECONDS);
    }

    @GetMapping(value = "connected", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConnectedUserList() {
        List<User> users = UserStore.getInstance().getUsers();

        List<User> userListResponse = new ArrayList<>();
        for (User user : users) {
            userListResponse.add(user);
        }
        
        return new ResponseEntity<>(userListResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessageToUser(@RequestBody SendMessageRequest request) throws IOException {
        User user = UserStore.getInstance().getUser(request.getTo());
        user.getWebSocketSession().sendMessage(new TextMessage(request.getMessage()));

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(true);
        baseResponse.setMessage("Message Sent");

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
