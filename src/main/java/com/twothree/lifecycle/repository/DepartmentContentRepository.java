package com.twothree.lifecycle.repository;

import com.twothree.lifecycle.entity.DepartmentContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentContentRepository extends JpaRepository<DepartmentContent, UUID> {
    
    List<DepartmentContent> findByDepartmentIdAndIsActiveTrue(UUID departmentId);
    
    List<DepartmentContent> findByIsActiveTrue();
    
    Optional<DepartmentContent> findByIdAndIsActiveTrue(UUID id);
}

