package com.santosh.springwebsocket.handler;

import com.santosh.springwebsocket.dto.User;
import com.santosh.springwebsocket.service.UserStore;
import com.santosh.springwebsocket.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("Received msg : " + message.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        log.info("Connected ... " + session.getId());
        log.info("session : " + session);

        log.info("Query parameters : " + session.getUri().getQuery());
        Map<String, String> param = HttpUtil.getQueryMap(session.getUri().getQuery());

        String path = session.getUri().getPath();
        log.info("path : " + path);

        String name = param.get("username") + param.get("channel");
        User user = new User(name, param.get("username"), param.get("channel"), session.getId(), session);

        UserStore.getInstance().registerUser(user);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(String.format("Session %s closed because of %s", session.getId(), status.getReason()));

        log.info("session : " + session);

        User user = new User(null, null, null, session.getId(), null);

        UserStore.getInstance().unregisterUser(user);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        log.error("error occured at sender " + session, throwable);
    }
}


