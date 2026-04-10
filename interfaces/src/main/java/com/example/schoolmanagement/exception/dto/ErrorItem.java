package com.example.schoolmanagement.exception.dto;

import java.util.Map;

public record ErrorItem(String code, String message, Map<String, String> metadata) {}

