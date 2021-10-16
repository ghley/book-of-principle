package dev.biblyon.principle.event;

final class Message {
    public final String event;
    public final Object userData;

    private Message(String event) {
        this(event, null);
    }

    private Message(String event, Object userData) {
        this.event = event;
        this.userData = userData;
    }

    static Message of(String event) {
        return new Message(event);
    }

    static Message of(String event, Object object){
        return new Message(event, object);
    }

    public String getEvent() {
        return event;
    }

    public Object getUserData() {
        return userData;
    }
}