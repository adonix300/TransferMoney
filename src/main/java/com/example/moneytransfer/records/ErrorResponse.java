package com.example.moneytransfer.records;

import lombok.Builder;


@Builder
public record ErrorResponse(
        String message,
        String errorType) {
}
