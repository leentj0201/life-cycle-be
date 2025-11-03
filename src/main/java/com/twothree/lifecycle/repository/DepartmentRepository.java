package com.twothree.lifecycle.repository;

import com.twothree.lifecycle.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    
    List<Department> findByChurchIdAndIsActiveTrue(UUID churchId);
    
    List<Department> findByIsActiveTrue();
    
    Optional<Department> findByIdAndIsActiveTrue(UUID id);
}

