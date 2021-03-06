package com.ws;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 * This class handles text message inputs, websockets sessions and text message outputs
 * for binary messages extend from BinaryWebSocketHandler instead
 */
@Component
public class SocketHandler extends TextWebSocketHandler {

    final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    final boolean sendToAllSesions = true;

    /***
     * This method gets called when a message is received from a client
     * @param session The actual websocket session that triggers this method
     * @param message The actual recived message
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        Map<String, String> value = (Map<String, String>)new Gson().fromJson(message.getPayload(), Map.class);
        String name = value.get("name");
        TextMessage responseMessage = new TextMessage("Hello " + name + " !");
        if (sendToAllSesions) {
            //Send response to all connected sessions
            for (WebSocketSession webSocketSession : sessions) {
                webSocketSession.sendMessage(responseMessage);
            }
        } else {
            //Send response to current session only
            session.sendMessage(responseMessage);
        }
    }

    /***
     * This method gets called when a client establishes a connection
     * @param session The actual websocket session that triggers this method
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        //Add to the list of connected sessions.
        sessions.add(session);
    }

    /***
     * This method gets called when a client closes the connection
     * @param session The actual websocket session that triggers this method
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        //Remove to the list of connected sessions.
        sessions.remove(session);
        session.close();
    }
}