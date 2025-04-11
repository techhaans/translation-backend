package com.tech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.dto.LabelResponseDTO;
import com.tech.model.Label;
import com.tech.reprositroy.labelRepository;

@Service
public class labelService {
	
	@Autowired
	private labelRepository labelRepository;
	
	public List<Label> getAllData()
	{
		return labelRepository.findAll();
		
	}
// List<Label> getLabelsByCustomer(Long customerId);
// List<Label> getAllLabelsWithLanguages();

//	public static Label getLabelById(Long labelId) {
//		return labelRepository.findById(labelId).orElse(null);
//	}
//
//	public static Label updateTranslation(Long labelId, Map<String, String> updates) {
//		java.util.Optional<Label> byId = labelRepository.findById(labelId);
//
//		if (byId.isPresent()) {
//			Label label = byId.get();
//			label.setLabelTranslation(updates);
//			return labelRepository.save(label);
//		}
//		return null;

}
