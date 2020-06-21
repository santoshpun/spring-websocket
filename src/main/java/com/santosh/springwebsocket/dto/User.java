package com.santosh.springwebsocket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    private String userId;
    private String mobileNumber;
    private String channel;
    private String sessionId;
    private WebSocketSession webSocketSession;

    public User(String userId, String mobileNumber, String channel, String sessionId, WebSocketSession webSocketSession) {
        this.userId = userId;
        this.mobileNumber = mobileNumber;
        this.channel = channel;
        this.sessionId = sessionId;
        this.webSocketSession = webSocketSession;
    }
}