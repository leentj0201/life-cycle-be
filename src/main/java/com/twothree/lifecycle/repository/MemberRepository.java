package com.twothree.lifecycle.repository;

import com.twothree.lifecycle.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    
    List<Member> findByChurchIdAndIsActiveTrue(UUID churchId);
    
    List<Member> findByIsActiveTrue();
    
    Optional<Member> findByIdAndIsActiveTrue(UUID id);
    
    Optional<Member> findByLoginIdAndIsActiveTrue(String loginId);
    
    boolean existsByLoginId(String loginId);
}

