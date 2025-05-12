package com.aitrip.utils;

import com.openai.models.ChatModel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public  class ChatModelConverter implements AttributeConverter<ChatModel, String> {
    @Override
    public String convertToDatabaseColumn(ChatModel attribute) {
        return (attribute != null) ? attribute.asString() : null;
    }

    @Override
    public ChatModel convertToEntityAttribute(String dbData) {
        return (dbData != null) ? ChatModel.of(dbData) : null;
    }
}
