package com.example.cinemamanagment.model.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp()
    private Timestamp createAt;

    @UpdateTimestamp
    private Timestamp updateAt;

    public AbstractEntity(Long id) {
        this.id = id;
    }

    // for auditing
    // private boolean deleted = Boolean.FALSE;


//    @CreatedBy
//    @JoinColumn(updatable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User createdBy;
//
//    @LastModifiedBy
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User updatedBy;
}
