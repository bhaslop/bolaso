package com.bolaso.service;

import com.bolaso.dto.Player;
import com.bolaso.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository repository;

    public void save(Player player) {
        repository.save(player);
    }

    public void save(String firstName, String lastName, String email, String nickname) {
        Player player = new Player();

        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setEmail(email);
        player.setNickname(nickname);

        save(player);
    }

    public Player getByEmail(String email) {
        return repository.findByEmail(email);
    }
}
