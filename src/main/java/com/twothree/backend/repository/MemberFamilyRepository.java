package com.twothree.backend.repository;

import com.twothree.backend.entity.MemberFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberFamilyRepository extends JpaRepository<MemberFamily, UUID> {
    
    List<MemberFamily> findByMemberId(UUID memberId);
    
    Optional<MemberFamily> findById(UUID id);
}

