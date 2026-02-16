package com.ectrvia.trivia.kafka.consumer;

import com.ectrvia.trivia.kafka.event.AnswerSubmittedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reference consumer for answer events.
 */
// @Service
public class AnswerEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AnswerEventConsumer.class);

    public void consumeAnswerSubmitted(AnswerSubmittedEvent event) {
        logger.debug("Received answer submitted event (kafka-service): eventId={}, roomCode={}, playerId={}",
                event.getEventId(), event.getRoomCode(), event.getPlayerId());
        // Consumer hook
    }
}
