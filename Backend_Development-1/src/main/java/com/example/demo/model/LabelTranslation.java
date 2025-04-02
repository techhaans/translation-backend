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
@Table(name = "label_translation")
@Data
public class LabelTranslation 
{
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long labelTranslationId;
	    
	    private String language;
	    private String labelTranslated;
	    private String status;
	    
	    @ManyToOne
	    @JoinColumn(name = "approved_by")
	    private ProofReader proofReader;

	    @CreationTimestamp
	    private LocalDateTime createdDate;

	    @UpdateTimestamp
	    private LocalDateTime updatedDate;

	    
	

}
