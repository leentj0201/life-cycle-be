package com.twothree.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
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
@Table(name = "member_setting")
public class MemberSetting extends BaseEntity {

    @Id
    @Column(name = "member_setting_id", nullable = false)
    private UUID id;

    @PrePersist
    private void prePersistAssignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    @NotNull
    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @ColumnDefault("true")
    @Column(name = "is_notification")
    private Boolean isNotification;

}