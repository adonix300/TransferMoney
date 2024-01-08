package com.example.moneytransfer.service;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.model.ConfirmOperationBody;
import com.example.moneytransfer.model.TransferAmount;
import com.example.moneytransfer.model.TransferBody;
import com.example.moneytransfer.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TransferServiceTest {

    @Mock
    TransferRepository transferRepository;
    @Mock
    Logger logger;
    @InjectMocks
    TransferService transferService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    static Stream<Arguments> invalidTransferBodyData() {
        return Stream.of(
                Arguments.of(new TransferBody(null, "12/25",
                        "123", "2345678923456789", new TransferAmount(12345, "RUR"))), //Invalid cardFromNumber (null)
                Arguments.of(new TransferBody("1234567", "12/25", "123",
                        "2345678923456789", new TransferAmount(12345, "RUR"))), //Invalid cardFromNumber (length < 16)
                Arguments.of(new TransferBody("123456742332424424242123", "12/25", "123",
                        "2345678923456789", new TransferAmount(12345, "RUR"))), //Invalid cardFromNumber (length > 16)
                Arguments.of(new TransferBody("1234567812345678", null, "345",
                        "2345678923456789", new TransferAmount(12345, "RUR"))), //Invalid cardFromTill (null)
                Arguments.of(new TransferBody("1234567812345678", "12/53", null,
                        "2345678923456789", new TransferAmount(12345, "RUR"))), //Invalid cardFromCVV (null)
                Arguments.of(new TransferBody("1234567812345678", "12/53", "1",
                        "2345678923456789", new TransferAmount(12345, "RUR"))), //Invalid cardFromCVV (length < 3)
                Arguments.of(new TransferBody("1234567812345678", "12/53", "5231",
                        "2345678923456789", new TransferAmount(12345, "RUR"))), //Invalid cardFromCVV (length > 3)
                Arguments.of(new TransferBody("1234567812345678", "12/53", "146",
                        null, new TransferAmount(12345, "RUR"))), //Invalid cardToNumber (null)
                Arguments.of(new TransferBody("1234567812345678", "12/53", "146",
                        "222222", new TransferAmount(12345, "RUR"))), //Invalid cardToNumber (length < 16)
                Arguments.of(new TransferBody("1234567812345678", "12/53", "146",
                        "11111111111111111111", new TransferAmount(12345, "RUR"))), //Invalid cardToNumber (length > 16)
                Arguments.of(new TransferBody("1234567812345678", "12/53", "422",
                        "2345678923456789", new TransferAmount(null, "RUR"))), //Invalid transferAmount (value is null)
                Arguments.of(new TransferBody("1234567812345678", "12/53", "422",
                        "2345678923456789", new TransferAmount(12312, null))), //Invalid transferAmount (currency is null)
                Arguments.of(new TransferBody(null, null, null, null, new TransferAmount(null, null))) // All Invalid
        );
    }

    @ParameterizedTest
    @MethodSource("invalidTransferBodyData")
    void validationTransferBody_InvalidData_ShouldLogError(TransferBody transferBody) {
        assertThrows(ValidationException.class, () -> transferService.validationTransferBody(transferBody));
        verify(logger, times(1)).log(anyString());
    }

    static Stream<Arguments> invalidConfirmOperationBody() {
        return Stream.of(Arguments.of(new ConfirmOperationBody("1", null)), //Invalid code (null)
                Arguments.of(new ConfirmOperationBody("1", "123")), //Invalid code (length < 4)
                Arguments.of(new ConfirmOperationBody("1","12345")), //Invalid code (length > 4)
                Arguments.of(new ConfirmOperationBody("-1", "1234")) //Invalid id (length < 0)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidConfirmOperationBody")
    void validationConfirmOperationBody_InvalidData_ShouldLogErorr(ConfirmOperationBody confirmOperationBody){
        assertThrows(ValidationException.class, () -> transferService.validationConfirmOperation(confirmOperationBody));
        verify(logger, times(1)).log(anyString());
    }

    @Test
    void addTransfer_ValidTransferBody_ShouldCallRepository() {
        TransferBody transferBody = new TransferBody("1234567812345678", "12/25", "234", "2345678923456789", new TransferAmount(43424, "RUR"));
        transferService.addTransfer(transferBody);
        verify(transferRepository).addTransfer(transferBody);
    }

    @Test
    void confirmOperation_ValidCode_ShouldCallRepository() {
        ConfirmOperationBody confirmOperationBody = new ConfirmOperationBody("1", "0000"); //valid code

        transferService.confirmOperation(confirmOperationBody);

        verify(transferRepository, times(1)).confirmOperation(anyString());
        verify(logger, never()).log(anyString());
    }

    @Test
    void confirmOperation_WrongCode_ShouldLogAndCallRepositoryFailed() {
        ConfirmOperationBody confirmOperationBody = new ConfirmOperationBody("1", "2335"); //wrong code

        assertNull(transferService.confirmOperation(confirmOperationBody));
        verify(logger, times(1)).log(anyString());
        verify(transferRepository, times(1)).confirmOperationFailed(anyString());
    }
}