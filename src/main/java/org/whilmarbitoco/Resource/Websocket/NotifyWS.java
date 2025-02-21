package org.whilmarbitoco.Resource.Websocket;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

@ServerEndpoint("/notify/{type}")
@ApplicationScoped
public class NotifyWS {

    Map<WSKey, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("type") String type) {
        System.out.println("onOpen> " + type);
        WSKey key = new WSKey();
        key.type = type;
        sessions.put(key, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("type") String type) {
        System.out.println("onClose> " + type);
    }

    @OnError
    public void onError(Session session, @PathParam("type") String type, Throwable throwable) {
        System.out.println("onError> " + type + ": " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("type") String type) {
        sendTo(type, message);
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }

    private void sendTo(String type, String message) {
        sessions.forEach((t, s) -> {
            if (t.type.equals(type)) {
                s.getAsyncRemote().sendObject(message, res -> {
                    if (res.getException() != null) System.out.println("Unable to send message: " + res.getException());
                });
            }
        });
    }


    static class WSKey {
        String type;
    }

}
