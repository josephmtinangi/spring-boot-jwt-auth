package com.example.hello.repositories;

import com.example.hello.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findFirstByUsername(String username);
}
