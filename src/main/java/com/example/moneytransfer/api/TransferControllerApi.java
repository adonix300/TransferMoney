package com.example.moneytransfer.api;


import com.example.moneytransfer.records.ConfirmOperationBody;
import com.example.moneytransfer.records.ConfirmOperationResponse;
import com.example.moneytransfer.records.TransferBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Validated
public interface TransferControllerApi {
    @RequestMapping(value = "/transfer",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<ConfirmOperationResponse> transferPost(@RequestBody TransferBody body);

    @RequestMapping(value = "/confirmOperation",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<ConfirmOperationResponse> confirmOperationPost(@RequestBody ConfirmOperationBody body);
}

