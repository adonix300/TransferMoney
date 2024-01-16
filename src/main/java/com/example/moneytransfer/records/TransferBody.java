package com.example.moneytransfer.records;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;


public record TransferBody(
        @JsonProperty("cardFromNumber") String cardFromNumber,
        @JsonProperty("cardFromValidTill") String cardFromValidTill,
        @JsonProperty("cardFromCVV") String cardFromCVV,
        @JsonProperty("cardToNumber") String cardToNumber,
        @JsonProperty("amount") TransferAmount amount
) {
}
