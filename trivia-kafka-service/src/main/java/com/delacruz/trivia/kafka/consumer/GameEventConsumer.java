package com.delacruz.trivia.kafka.consumer;

import com.delacruz.trivia.kafka.event.GameStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reference consumer for game events.
 */
// @Service
public class GameEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(GameEventConsumer.class);

    public void consumeGameEvent(GameStateEvent event) {
        logger.debug("Received game event (kafka-service): eventId={}, roomCode={}, eventType={}",
                event.getEventId(), event.getRoomCode(), event.getEventType());
        // Consumer hook
    }
}
