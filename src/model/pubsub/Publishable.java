package model.pubsub;

import model.mqtt.MqttMessage;

public interface Publishable {
    void publish(MqttMessage message);
}
