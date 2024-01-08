package com.example.moneytransfer.api;


import com.example.moneytransfer.model.ConfirmOperationBody;
import com.example.moneytransfer.model.TransferBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Validated
public interface TransferApi {
    @RequestMapping(value = "/transfer",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<?> transferPost(@RequestBody TransferBody body);

    @RequestMapping(value = "/confirmOperation",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<?> confirmOperationPost(@RequestBody ConfirmOperationBody body);
}

