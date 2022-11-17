package model.mqtt;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class MqttMessage implements Serializable {
    private static final long serialVersionUID = -3075617026078137668L;
    private final long clientId;
    private final String topic;
    private final int type;
    private final Object payload;

    private ByteBuffer bufferString;

    private String resString;

    public String getResString() {
        return resString;
    }

    public void setResString(String resString) {
        this.resString = resString;
    }
//    public MqttMessage(long clientId, String topic, int type, Object payload) {
//        this.clientId = clientId;
//        this.topic = topic;
//        this.type = type;
//        this.payload = payload;
//    }


    public MqttMessage(long clientId, String topic, int type, Object payload, ByteBuffer bufferString) {
        this.clientId = clientId;
        this.topic = topic;
        this.type = type;
        this.payload = payload;
        this.bufferString = bufferString;
    }

    public long getClientId() {
        return clientId;
    }

    public String getTopic() {
        return topic;
    }

    public Object getPayload() {
        return payload;
    }

    public int getType() {
        return type;
    }

    public MqttMessage copy(Object payload) {
        return new MqttMessage(
                clientId,
                topic,
                type,
                payload,
                bufferString
        );
    }

    public void setBufferString(ByteBuffer bufferString) {
        this.bufferString = bufferString;
    }

    public ByteBuffer getBufferString() {
        return bufferString;
    }

    @Override
    public String toString() {
        return "{" + "\n" +
                "clientId: " + clientId + "\n" +
                "topic: " + topic + "\n" +
                "type: " + type + "\n" +
                "payload: " + payload + "\n" +
                "}";
    }

}
