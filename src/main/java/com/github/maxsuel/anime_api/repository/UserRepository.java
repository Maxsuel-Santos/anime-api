package com.github.maxsuel.anime_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.github.maxsuel.anime_api.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
