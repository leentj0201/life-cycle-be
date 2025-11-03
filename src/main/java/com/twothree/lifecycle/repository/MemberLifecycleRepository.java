package com.twothree.lifecycle.repository;

import com.twothree.lifecycle.entity.MemberLifecycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberLifecycleRepository extends JpaRepository<MemberLifecycle, UUID> {
    
    List<MemberLifecycle> findByMemberIdAndIsActiveTrue(UUID memberId);
    
    List<MemberLifecycle> findByLifecycleIdAndIsActiveTrue(UUID lifecycleId);
    
    List<MemberLifecycle> findByIsActiveTrue();
    
    Optional<MemberLifecycle> findByIdAndIsActiveTrue(UUID id);
}

