package com.ectrvia.trivia.transform;

import com.ectrvia.trivia.entity.RoomData;
import com.ectrvia.trivia.model.Room;

public interface TransformRoomService {
    Room transform(RoomData roomData);
    RoomData transform(Room room);
}
