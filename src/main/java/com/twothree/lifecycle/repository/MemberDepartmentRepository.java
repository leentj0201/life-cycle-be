package com.twothree.lifecycle.repository;

import com.twothree.lifecycle.entity.MemberDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberDepartmentRepository extends JpaRepository<MemberDepartment, UUID> {
    
    List<MemberDepartment> findByMemberIdAndIsActiveTrue(UUID memberId);
    
    List<MemberDepartment> findByDepartmentIdAndIsActiveTrue(UUID departmentId);
    
    List<MemberDepartment> findByIsActiveTrue();
    
    Optional<MemberDepartment> findByIdAndIsActiveTrue(UUID id);
}

