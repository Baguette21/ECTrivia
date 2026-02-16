package com.ectrvia.trivia.service;

import com.ectrvia.trivia.model.Room;
import com.ectrvia.trivia.model.Player;

public interface RoomService {
    Room createRoom(Long categoryId, Boolean isThemeBased, Integer timerSeconds, Integer maxPlayers);
    Room getRoomByCode(String roomCode);
    Player joinRoom(String roomCode, String nickname);
    void leaveRoom(String roomCode, Long playerId);
    Room startGame(String roomCode, Long playerId);
    void updateHostPlayer(String roomCode, Long newHostPlayerId);
}
