package com.example.moneytransfer.validators;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.logger.Logger;
import com.example.moneytransfer.records.TransferAmount;
import com.example.moneytransfer.records.TransferBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class TransferValidatorTest {
    @Mock
    Logger logger;
    @InjectMocks
    TransferValidator transferValidator;
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
        assertThrows(ValidationException.class, () -> transferValidator.validateTransferBody(transferBody));
        verify(logger, times(1)).log(anyString());
    }

}
