package model;

import com.google.gson.Gson;
import model.mqtt.Listener;
import model.mqtt.MqttConnection;
import model.mqtt.MqttListener;
import model.pubsub.MqttMessage;
import model.pubsub.Publisher;
import model.pubsub.Subscriber;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
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

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
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
        conns.remove(conn);
//        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String jsonString) {
        MqttMessage mqttMessage = gson.fromJson(jsonString, MqttMessage.class);
        mqttConnection.handleMessage(mqttMessage, conn);
        System.out.println(mqttMessage);
        System.out.println("publisher ne: " + mqttConnection.getPublishers());

//        for (WebSocket sock : conns) {
//            sock.send(message);
//        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            conns.remove(conn);
            // do some thing if required
        }
//        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    public static void main(String[] args) {
        WebsocketServer websocketServer = new WebsocketServer();
        websocketServer.start();
    }
}