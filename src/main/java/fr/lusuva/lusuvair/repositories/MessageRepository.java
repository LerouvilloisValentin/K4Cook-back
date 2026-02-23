package fr.k4cook.K4Cook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.k4cook.K4Cook.entities.Message;

/**
 * Message Repository
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {
    
}
