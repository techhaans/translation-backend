package com.example.demo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "labels")
@Data
public class Label 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long labelId;

    private String labelName;

    @ManyToOne
    @JoinColumn(name = "label_translation_id")
    private LabelTranslation labelTranslation;

    @ManyToOne
    @JoinColumn(name = "cid")
    private Customer customer;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

}
