package com.twothree.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "member_family")
public class MemberFamily extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_family_id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @NotNull
    @Column(name = "family_member_id", nullable = false)
    private UUID familyMemberId;

    @Size(max = 50)
    @NotNull
    @Column(name = "relation_type", nullable = false, length = 50)
    private String relationType;

}