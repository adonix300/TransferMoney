package com.example.moneytransfer.validators;

import com.example.moneytransfer.exception.ValidationException;
import com.example.moneytransfer.records.ConfirmOperationBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConfirmOperationValidatorTest {
    @InjectMocks
    ConfirmOperationValidator confirmOperationValidator;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    static Stream<Arguments> invalidConfirmOperationBody() {
        return Stream.of(Arguments.of(new ConfirmOperationBody("1", null)), //Invalid code (null)
                Arguments.of(new ConfirmOperationBody("1", "123")), //Invalid code (length < 4)
                Arguments.of(new ConfirmOperationBody("1", "12345")), //Invalid code (length > 4)
                Arguments.of(new ConfirmOperationBody("-1", "1234")) //Invalid id (length < 0)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidConfirmOperationBody")
    void validationConfirmOperationBody_InvalidData_ShouldLogError(ConfirmOperationBody confirmOperationBody) {
        assertThrows(ValidationException.class, () -> confirmOperationValidator.validate(confirmOperationBody));
    }

    @Test
    void validateConfirmOperationOnWrongCode_ShouldNotThrowValidationException() {
        String id = "1";
        String code = "0000"; // correct code
        ConfirmOperationBody confirmOperationBody = new ConfirmOperationBody(id, code);
        assertDoesNotThrow(() -> confirmOperationValidator.validate(confirmOperationBody));
    }
}
