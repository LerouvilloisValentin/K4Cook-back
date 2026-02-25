package fr.k4cook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.k4cook.entities.Message;

/**
 * Message Repository
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
