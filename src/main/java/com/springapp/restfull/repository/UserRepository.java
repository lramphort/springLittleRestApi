package com.springapp.restfull.repository;

import com.springapp.restfull.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}