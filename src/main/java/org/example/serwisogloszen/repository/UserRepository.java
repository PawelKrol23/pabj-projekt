package org.example.serwisogloszen.repository;

import org.example.serwisogloszen.model.Category;
import org.example.serwisogloszen.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);
}
