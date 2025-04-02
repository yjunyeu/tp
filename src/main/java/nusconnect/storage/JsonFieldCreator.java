package nusconnect.storage;

import java.util.function.Function;

/**
 * A utility class for creating objects based on input fields.
 * This class provides a method to check if an input string is null,
 * and then constructs an object using a given constructor if the input is non-null.
 * If the input is null, a null is returned.
 */
public class JsonFieldCreator {

    /**
     * Creates an object using a constructor
     * if the input is non-null, the method a field of type T.
     * If the input is null, the method returns null.
     *
     * @param <T> The type of object to be created.
     * @param input The input string to be validated and used to create the object.
     * @param constructor A constructor function that creates the object from the input string.
     * @return The created object if the input is valid, or null if the input is null.
     */
    public static <T> T createField(String input,
                                      Function<String, T> constructor) {
        if (input != null) {
            return constructor.apply(input);
        } else {
            return null;
        }
    }
}
