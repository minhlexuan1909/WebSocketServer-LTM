package model.pubsub;

public interface Publishable {
    void publish(MqttMessage message);
}
