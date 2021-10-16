package dev.biblyon.principle.event;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class EventSystemTest {

    @Test
    public void testWrongEvent() {
        AtomicBoolean notTrue = new AtomicBoolean(false);
        EventSystem.subscribe("wrong", (v)->{
            notTrue.set(true);
        });
        EventSystem.call("correct");
        EventSystem.pollEvents();
        assertFalse(notTrue.get());
    }

    @Test
    public void testCorrectEvent() {
        AtomicBoolean notFalse = new AtomicBoolean(false);
        EventSystem.subscribe("correct", (v)->{
            notFalse.set(true);
        });
        EventSystem.call("correct");
        assertFalse(notFalse.get());
        EventSystem.pollEvents();
        assertTrue(notFalse.get());
    }
}