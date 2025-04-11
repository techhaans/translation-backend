package com.tech.dto;

import java.util.List;

import lombok.Data;
@Data
public class LabelResponseDTO 
{
	   private Long labelId;
	    private String label;
	    private List<String> languages;
	    private boolean isApproved;
}
