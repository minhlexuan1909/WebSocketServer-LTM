package model.mqtt;

public interface MqttListener {
    void onMqttConnect(MqttMessage message);
    void onMqttPublish(MqttMessage message);
    void onMqttSubscribe(MqttMessage message);
    void onMqttUnSubscribe(MqttMessage message);
    void onMqttDisconnect(MqttMessage message);
    void onMqttError(MqttMessage message, String reason);
}
