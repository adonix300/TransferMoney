package com.example.moneytransfer.api;

import com.example.moneytransfer.model.ConfirmOperationBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Validated
public interface ConfirmOperationApi {

    @RequestMapping(value = "/confirmOperation",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<?> confirmOperationPost(@RequestBody ConfirmOperationBody body);
}

