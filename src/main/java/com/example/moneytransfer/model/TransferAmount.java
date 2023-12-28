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
public class TransferAmount {
    @JsonProperty("value")
    private Integer value = null;

    @JsonProperty("currency")
    private String currency = null;

}
