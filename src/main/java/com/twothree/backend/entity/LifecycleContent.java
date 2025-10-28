package com.twothree.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "lifecycle_content")
public class LifecycleContent extends BaseEntity {

    @Id
    @Column(name = "lifecycle_content_id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "church_id", nullable = false)
    private UUID churchId;

    @NotNull
    @Column(name = "lifecycle_id", nullable = false)
    private UUID lifecycleId;

    @Size(max = 50)
    @NotNull
    @Column(name = "lifecycle_content_type", nullable = false, length = 50)
    private String lifecycleContentType;

    @Size(max = 200)
    @NotNull
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

}