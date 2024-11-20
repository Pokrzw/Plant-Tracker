package com.example.Plant_tracker.repositories;

import com.example.Plant_tracker.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    
}
