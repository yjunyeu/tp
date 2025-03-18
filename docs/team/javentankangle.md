Project: NUSConnect
NUSConnect is a desktop address book application used for teaching Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **Restructured the Data Model**:
  * Introduced new classes to the model, such as Alias, Course, Note, Person, Telegram, and Website, to better organize and represent the data structure.
  * Ensured that each class accurately reflects the relevant properties and behaviors of the corresponding entities within the project.


* **Refactored the Edit/Add Command**:
  * Updated the logic behind the edit and add commands to accommodate the new data structure.
  * Made sure that these commands are capable of handling the new classes and their relationships


* **Modified the Parser**:
  * Adjusted the parser to recognize and properly handle inputs for the newly added fields and models.
  * Updated parsing logic to include the new attributes and commands related to the refactored structure.


* **Updated the UI**:
  * Made changes to the user interface to reflect the new data structure


* **Ensured Backward Compatibility**:
  * Ensure existing functionality and previous data were still compatible with the new changes, ensuring smooth operation without introducing bugs or breaking existing features.


* **Testing and Validation**:
  * Wrote and updated unit tests to verify that the new classes and commands work as expected.
  * Performed integration testing to ensure that changes to the model, parser, and UI worked together without causing issues in the project flow.


* **Documentation Updates**:
  * Updated internal documentation and code comments to reflect changes in the data model and the updated workflows.