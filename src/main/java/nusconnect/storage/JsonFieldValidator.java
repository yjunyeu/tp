package nusconnect.storage;

import java.util.function.Function;

import nusconnect.commons.exceptions.IllegalValueException;

/**
 * A utility class for validating and creating objects based on input fields.
 * This class provides a method to check if an input string is valid according to a validation function,
 * and then constructs an object using a given constructor if the input is valid.
 * If the input is invalid, an IllegalValueException is thrown.
 */
public class JsonFieldValidator {

    /**
     * Validates the input string using a provided validation function, and creates an object using a constructor
     * if the input is valid. If the input is invalid, an IllegalValueException is thrown.
     * If the input is null, the method returns null.
     *
     * @param <T> The type of object to be created.
     * @param input The input string to be validated and used to create the object.
     * @param isValid A validation function that checks if the input string is valid.
     * @param constructor A constructor function that creates the object from the input string.
     * @param errorMessage The error message to be used in the exception if the input is invalid.
     * @return The created object if the input is valid, or null if the input is null.
     * @throws IllegalValueException If the input is invalid according to the validation function.
     */
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
