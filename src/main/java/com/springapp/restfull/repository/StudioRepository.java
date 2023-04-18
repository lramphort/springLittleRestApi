package com.springapp.restfull.repository;

import com.springapp.restfull.entities.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepository extends JpaRepository<Studio, Long> {
}