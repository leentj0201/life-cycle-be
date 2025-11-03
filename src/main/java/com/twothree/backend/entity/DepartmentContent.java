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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity
@Table(name = "department_content")
public class DepartmentContent extends BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "department_content_id", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "church_id", nullable = false)
    private UUID churchId;

    @NotNull
    @Column(name = "department_id", nullable = false)
    private UUID departmentId;

    @Size(max = 50)
    @NotNull
    @Column(name = "department_content_type", nullable = false, length = 50)
    private String departmentContentType;

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