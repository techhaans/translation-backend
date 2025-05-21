package com.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class LabelRequestDTO {

    @NotEmpty(message = "Label data must not be empty")
    private Map<String, String> labelKeyValuePairs;

    public Map<String, String> getLabelKeyValuePairs() {
        return labelKeyValuePairs;
    }

    public void setLabelKeyValuePairs(Map<String, String> labelKeyValuePairs) {
        this.labelKeyValuePairs = labelKeyValuePairs;
    }
}
