Project: NUSConnect
NUSConnect is a desktop address book application used for teaching Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.


* **Extended Find functionality**:
    * Refactored the FindCommand to allow searching by both names and modules
    * Modified the command's execution logic to handle both predicates and update the filtered list accordingly
    * Refactored the FindCommand to allow partial string matches for both names and modules


* **Modified the FindCommandParser**:
    * Modified the FindCommandParser to support parsing inputs for both name and module keywords.
    * Enhanced the parsing logic to properly handle both multiple keywords and match them against both the name and module fields.


* **Created the ModuleContainsKeywordsPredicate**:
    * Implemented a new predicate class (ModuleContainsKeywordsPredicate) to filter persons based on module names that match the specified keywords.


* **Implemented sort functionality**:
  * Added the `sort` command to sort the addressbook in alphabetical order for better usability


* **Ensured Backward Compatibility**:
    * Ensure existing functionality and previous data were still compatible with the new changes, ensuring smooth operation without introducing bugs or breaking existing features.


* **Testing and Validation**:
    * Wrote and updated unit tests to verify that the new FindCommand functionality works as expected.
    * Add test cases for the ModuleContainsKeywordsPredicate and ensured both predicates are correctly handled in the command.


* **Documentation Updates**:
    * User guide
      * Updated documentation and code comments to reflect changes in the FindCommand.
      * Modified command usage instructions to include module search capability.
      * Wrote documentation and code comments for `sort` command

   * Developer guide
     * Updated model's UML class diagrams to reflect updated fields for person
