package com.twothree.backend.repository;

import com.twothree.backend.entity.LifecycleContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LifecycleContentRepository extends JpaRepository<LifecycleContent, UUID> {
    
    List<LifecycleContent> findByLifecycleIdAndIsActiveTrue(UUID lifecycleId);
    
    List<LifecycleContent> findByIsActiveTrue();
    
    Optional<LifecycleContent> findByIdAndIsActiveTrue(UUID id);
}

