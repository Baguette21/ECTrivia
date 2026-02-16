package com.ectrvia.trivia.transform;

import com.ectrvia.trivia.entity.PlayerData;
import com.ectrvia.trivia.model.Player;

public interface TransformPlayerService {
    Player transform(PlayerData playerData);
    PlayerData transform(Player player);
}
