package com.example.moneytransfer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferBody {
    @JsonProperty("cardFromNumber")
    private String cardFromNumber = null;

    @JsonProperty("cardFromValidTill")
    private String cardFromValidTill = null;

    @JsonProperty("cardFromCVV")
    private String cardFromCVV = null;

    @JsonProperty("cardToNumber")
    private String cardToNumber = null;

    @JsonProperty("amount")
    private TransferAmount amount = null;
}
