package model.pubsub;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import model.mqtt.MqttMessage;

public class Publisher implements Publishable{
    private final String topic;
    private final List<Subscriber> subscribers;

    public Publisher(String topic) {
        this.topic = topic;
        subscribers = new ArrayList<>();
    }

    public Publisher(String topic, List<Subscriber> subscribers) {
        this.topic = topic;
        this.subscribers = subscribers;
    }

    public String getTopic() {
        return topic;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void addSubscriber(Subscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void connectSubscriber(Subscriber subscriber) {
        int size = subscribers.size();
        for (int i = 0; i < size; i++) {
            if (subscriber.equals(subscribers.get(i))) {

            }
        }
    }

    public boolean isValidSubscriber(Subscriber subscriber) {
        for (Subscriber sub : this.subscribers) {
            if (subscriber.equals(sub)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void publish(MqttMessage message) {
        System.out.println("Hi");
        System.out.println(this.subscribers);
        for (Subscriber subscriber : this.subscribers) {
            if (subscriber.isOpen() && subscriber.getClientId() != message.getClientId()) {
//                subscriber.getWebSocket().send(message);
                Gson gson = new Gson();
//                subscriber.getWebSocket().send(gson.toJson(message.getPayload()));
                byte[] byteArrray = message.getResString().getBytes();
                System.out.println("buffer string: " + message.getResString());
                subscriber.getWebSocket().send(byteArrray);
                System.out.println("send message to " + subscriber.getWebSocket());
            }
        }
    }
}
