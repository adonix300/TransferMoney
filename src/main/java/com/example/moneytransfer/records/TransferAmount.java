package com.example.moneytransfer.records;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransferAmount(
        @JsonProperty("value") Integer value,
        @JsonProperty("currency") String currency
) {
    @Override
    public String toString() {
        return (value / 100) + " " + currency;
    }
}

