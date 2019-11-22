package com.bolaso.repository;

import com.bolaso.dto.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, String> {
    public Player findByEmail(String email);
    public Player findMyNickname(String nickname);
}
