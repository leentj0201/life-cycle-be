package com.twothree.backend.repository;

import com.twothree.backend.entity.Lifecycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LifecycleRepository extends JpaRepository<Lifecycle, UUID> {
    
    List<Lifecycle> findByChurchIdAndIsActiveTrue(UUID churchId);
    
    List<Lifecycle> findByIsActiveTrue();
    
    Optional<Lifecycle> findByIdAndIsActiveTrue(UUID id);
}

