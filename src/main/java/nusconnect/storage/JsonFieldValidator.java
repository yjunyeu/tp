package nusconnect.storage;

import java.util.function.Function;

import nusconnect.commons.exceptions.IllegalValueException;

public class JsonFieldValidator {
    public static <T> T validateField(String input,
                                      Function<String, Boolean> isValid,
                                      Function<String, T> constructor,
                                      String errorMessage) throws IllegalValueException {
        if (input != null) {
            if (!isValid.apply(input)) {
                throw new IllegalValueException(errorMessage);
            }
            return constructor.apply(input);
        } else {
            return null;
        }
    }
}
