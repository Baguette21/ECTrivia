package com.ectrvia.trivia.kafka.consumer;

import com.ectrvia.trivia.kafka.event.ScoreUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reference consumer for score events.
 */
// @Service
public class ScoreEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ScoreEventConsumer.class);

    public void consumeScoreUpdated(ScoreUpdatedEvent event) {
        logger.debug("Received score updated event (kafka-service): eventId={}, roomCode={}, playerId={}, points={}",
                event.getEventId(), event.getRoomCode(), event.getPlayerId(), event.getPointsEarned());
        // Consumer hook
    }
}
