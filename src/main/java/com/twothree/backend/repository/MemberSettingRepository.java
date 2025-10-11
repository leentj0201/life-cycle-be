package com.twothree.backend.repository;

import com.twothree.backend.entity.MemberSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberSettingRepository extends JpaRepository<MemberSetting, UUID> {
    
    Optional<MemberSetting> findByMemberId(UUID memberId);
}

