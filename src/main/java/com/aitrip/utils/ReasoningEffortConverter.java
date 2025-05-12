package com.aitrip.utils;

import com.openai.models.ReasoningEffort;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ReasoningEffortConverter implements AttributeConverter<ReasoningEffort, String> {

    @Override
    public String convertToDatabaseColumn(ReasoningEffort attribute) {
        return (attribute != null) ? attribute.asString() : null;
    }

    @Override
    public ReasoningEffort convertToEntityAttribute(String dbData) {
        return (dbData != null) ? ReasoningEffort.of(dbData) : null;
    }
}
