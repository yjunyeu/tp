package nusconnect.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import nusconnect.commons.util.ToStringBuilder;
import nusconnect.logic.Messages;
import nusconnect.model.Model;
import nusconnect.model.person.ModuleContainsKeywordsPredicate;
import nusconnect.model.person.NameContainsKeywordsPredicate;
import nusconnect.model.person.Person;

/**
 * Finds and lists all persons in address book whose name or module contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names or modules "
            + "contain any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob cs2103t";

    private final NameContainsKeywordsPredicate namePredicate;
    private final ModuleContainsKeywordsPredicate modulePredicate;
    private final Predicate<Person> combinedPredicate;

    /**
     * Creates a FindCommand to find persons based on the given name and module predicates.
     *
     * @param namePredicate the predicate to search by name
     * @param modulePredicate the predicate to search by module
     */
    public FindCommand(NameContainsKeywordsPredicate namePredicate, ModuleContainsKeywordsPredicate modulePredicate) {
        this.namePredicate = namePredicate;
        this.modulePredicate = modulePredicate;
        this.combinedPredicate = namePredicate.or(modulePredicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(combinedPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return namePredicate.equals(otherFindCommand.namePredicate)
                && modulePredicate.equals(otherFindCommand.modulePredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("modulePredicate", modulePredicate)
                .toString();
    }
}
