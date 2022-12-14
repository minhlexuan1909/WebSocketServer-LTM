package model;

import com.google.gson.Gson;
import model.java_websocket.WebSocket;
import model.java_websocket.server.WebSocketServer;
import model.mqtt.Listener;
import model.mqtt.MqttConnection;
import model.mqtt.MqttMessage;
import model.pubsub.Publisher;
import model.pubsub.Subscriber;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WebsocketServer extends WebSocketServer {

    private Map<String, Publisher> publishers;
    private Listener listener;
    private Map<Long, List<String>> subscriberTopic;
    private Map<Long, Subscriber> subscribers;
    MqttConnection mqttConnection;

    Gson gson;
    private static int TCP_PORT = 4444;

    private Set<WebSocket> conns;

    public WebsocketServer() {
        super(new InetSocketAddress(TCP_PORT));
        conns = new HashSet<>();
        gson = new Gson();
    }


    //////////////////////////////////////////////////////

    @Override
    public void onOpen(model.java_websocket.WebSocket conn, model.java_websocket.handshake.ClientHandshake handshake) {
        conns.add(conn);
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onStart() {
        publishers = new HashMap<>();
        listener = new Listener();
        subscriberTopic = new HashMap<>();
        subscribers = new HashMap<>();
        mqttConnection = new MqttConnection(publishers, listener, subscriberTopic, subscribers);
        System.out.println("Server started");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        MqttMessage mqttMessage = gson.fromJson(message, MqttMessage.class);
        mqttConnection.handleMessage(mqttMessage, conn);
        System.out.println(mqttMessage);
        System.out.println("publisher ne: " + mqttConnection.getPublishers());
    }

    @Override
    public void onMessage(model.java_websocket.WebSocket conn, ByteBuffer message2) {
        System.out.println(message2);

        String temp = StandardCharsets.UTF_8.decode(message2).toString();
        String res = "";

        for(int i =0 ; i < temp.length(); i++){
            res += temp.charAt(i);
        }
        System.out.println("res: " + res);
        MqttMessage mqttMessage = gson.fromJson(res, MqttMessage.class);
        mqttMessage.setResString(res);
        mqttConnection.handleMessage(mqttMessage, conn);

        System.out.println(mqttMessage);


//        System.out.println(mqttMessage);
//        System.out.println("publisher ne: " + mqttConnection.getPublishers());


//        for (WebSocket sock : conns) {
//            sock.send(message);
//        }
    }

    @Override
    public void onError(model.java_websocket.WebSocket conn, Exception ex) {

    }


    //////////////////////////////////////////////////////





//    @Override
//    public void onOpen(WebSocket conn, ClientHandshake handshake) {
//        conns.add(conn);
//        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
//    }
//
//    @Override
//    public void onStart() {
//        publishers = new HashMap<>();
//        listener = new Listener();
//        subscriberTopic = new HashMap<>();
//        subscribers = new HashMap<>();
//        mqttConnection = new MqttConnection(publishers, listener, subscriberTopic, subscribers);
//        System.out.println("Server started");
//    }
//
//    @Override
//    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
//        conns.remove(conn);
////        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
//    }
//
//    @Override
//    public void onMessage( WebSocket conn, String jsonString) {
//        MqttMessage mqttMessage = gson.fromJson(jsonString, MqttMessage.class);
//        mqttConnection.handleMessage(mqttMessage, conn);
//        System.out.println(mqttMessage);
//        System.out.println("publisher ne: " + mqttConnection.getPublishers());
//
////        for (WebSocket sock : conns) {
////            sock.send(message);
////        }
//    }
//
//    @Override
//    public void onError(WebSocket conn, Exception ex) {
//        ex.printStackTrace();
//        if (conn != null) {
//            conns.remove(conn);
//            // do some thing if required
//        }
////        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
//    }

    public static void main(String[] args) {
        WebsocketServer websocketServer = new WebsocketServer();
        websocketServer.start();
    }
}