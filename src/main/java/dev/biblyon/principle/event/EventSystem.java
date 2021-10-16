package dev.biblyon.principle.event;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

public final class EventSystem {
    private static Deque<Message> messagesQueue = new LinkedList<>();
    private static Map<String, Consumer<Object>> subscribers = new HashMap<>();

    private EventSystem() {
    }

    public static void call(String event) {
        messagesQueue.addLast(Message.of(event));
    }

    public static void call(String event, Object object) {
        messagesQueue.addLast(Message.of(event, object));
    }

    public static void subscribe(String event, Consumer<Object> consumer) {
        subscribers.put(event, consumer);
    }

    public static void pollEvents() {
        while (!messagesQueue.isEmpty()) {
            Message message = messagesQueue.pollFirst();
            subscribers.getOrDefault(message.event, (data)->{}).accept(message.userData);
        }
    }
}
