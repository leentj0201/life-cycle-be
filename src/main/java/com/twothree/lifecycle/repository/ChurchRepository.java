package com.twothree.lifecycle.repository;

import com.twothree.lifecycle.entity.Church;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChurchRepository extends JpaRepository<Church, UUID> {
    
    List<Church> findByIsActiveTrue();
    
    Optional<Church> findByIdAndIsActiveTrue(UUID id);
}

