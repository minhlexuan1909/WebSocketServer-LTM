package model.mqtt;

import model.pubsub.MqttMessage;
import model.pubsub.Subscription;

public class Listener implements MqttListener{
    public Listener() {
    }

    @Override
    public void onMqttConnect(MqttMessage message) {

    }

    @Override
    public void onMqttPublish(MqttMessage message) {

    }

    @Override
    public void onMqttSubscribe(MqttMessage message) {
//        log.info("onMqttSubscribe: message" + message);
//        Subscription subscription = new Subscription(
//                message.getClientId(),
//                message.getTopic()
//        );
//        mqttManager.addSubscription(subscription);
    }

    @Override
    public void onMqttUnSubscribe(MqttMessage message) {

    }

    @Override
    public void onMqttDisconnect(MqttMessage message) {

    }

    @Override
    public void onMqttError(MqttMessage message, String reason) {

    }
}
