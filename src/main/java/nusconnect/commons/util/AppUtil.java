package nusconnect.commons.util;

import static java.util.Objects.requireNonNull;

import javafx.scene.image.Image;
import nusconnect.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    /**
     * Gets an {@code Image} from the specified path.
     */
    public static Image getImage(String imagePath) {
        requireNonNull(imagePath);
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException if {@code condition} is false.
     */
    public static void checkArgument(Boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that {@code condition} is true. Used for propagating warning messages to command result..
     */
    public static String checkArgument(Boolean condition, String warningMessage) {
        if (!condition) {
            return warningMessage + "\n";
        }
        return "";
    }

    /**
     * Checks that {@code condition} is true. Used for validating arguments to methods.
     *
     * @throws IllegalArgumentException with {@code errorMessage} if {@code condition} is false.
     */
    public static void checkArgumentAndThrow(Boolean condition, String errorMessage) {
        if (!condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
