package nusconnect.model.module;

import static java.util.Objects.requireNonNull;
import static nusconnect.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidModuleName(String)}
 */
public class Module {

    public static final String MESSAGE_CONSTRAINTS =
            "Module names should be alphanumeric, starting with 2~3 alphabets, followed by 4 numeric characters, " +
                    "and followed by an optional character.";
    public static final String VALIDATION_REGEX = "^[A-Za-z]{2,3}[0-9]{4}[A-Za-z]?$";

    public final String moduleName;

    /**
     * Constructs a {@code Module}.
     *
     * @param moduleName A valid module name.
     */
    public Module(String moduleName) {
        requireNonNull(moduleName);
        checkArgument(isValidModuleName(moduleName), MESSAGE_CONSTRAINTS);
        this.moduleName = moduleName.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid module name.
     */
    public static boolean isValidModuleName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return moduleName.equals(otherModule.moduleName);
    }

    @Override
    public int hashCode() {
        return moduleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + moduleName + ']';
    }

}
