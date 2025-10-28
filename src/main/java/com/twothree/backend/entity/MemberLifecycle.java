package com.twothree.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "member_lifecycle")
public class MemberLifecycle extends BaseEntity {

    @Id
    @Column(name = "member_lifecycle_id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @NotNull
    @Column(name = "lifecycle_id", nullable = false)
    private UUID lifecycleId;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

}