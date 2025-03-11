package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidModuleName(String)}
 */
public class Module {

    public static final String MESSAGE_CONSTRAINTS = "Module names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String ModuleName;

    /**
     * Constructs a {@code Module}.
     *
     * @param ModuleName A valid module name.
     */
    public Module(String ModuleName) {
        requireNonNull(ModuleName);
        checkArgument(isValidModuleName(ModuleName), MESSAGE_CONSTRAINTS);
        this.ModuleName = ModuleName;
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
        return ModuleName.equals(otherModule.ModuleName);
    }

    @Override
    public int hashCode() {
        return ModuleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + ModuleName + ']';
    }

}
