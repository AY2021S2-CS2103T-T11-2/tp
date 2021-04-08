---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ContactListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object which is executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a contact).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object also contains a `UiCommand` object, which encapsulates information needed to instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user’s preferences.
* stores the address book data.
* exposes an unmodifiable `ObservableList<Contact>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.


<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Contact` references. This allows `AddressBook` to only require one `Tag` object per unique `Tag`, instead of each `Contact` needing their own `Tag` object.<br>
![BetterModelClassDiagram](images/BetterModelClassDiagram.png)

</div>


### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the address book data in json format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Model for Tasks (Todos, Deadlines & Events)

The classes and implementations used to model various Tasks (e.g. Todos, Deadlines & Events) are facilitated by `CompletableTodo`, `CompletableDeadline` and '`Repeatable` abstract classes. This design is similar to the Contact model from AB3.

The client creates a concrete `Todo`, `Deadline` or `Event` that encapsulates all information related to these Tasks. Each concrete `Todo`, `Deadline` or `Event` implements the `CompletableTodo`, `CompletableDeadline` and '`Repeatable` abstract classes respectively. Each `Completable` and `Repeatable` abstract class specifies specific behaviors.

Given below is an example usage scenario and how the mechanism behaves at each step.

Step 1. The user executes the command `addD`, which adds a Deadline to a project specified in the command.

Step 2. The `Deadline` object is created during  the parsing of a user's command. The `Deadline` object requires a description & LocalDate.

Step 3. The `Deadline` object is then passed as a parameter in a created `AddDeadlineCommand` that would be executed.

Step 4. During it's execution, the `Deadline` object would be added to a `DeadlineList` that is stored in a `Project`.

#### Design Considerations

##### Aspect: How to store and pass around UI related instructions

* **Alternative 1 (current choice):** Implement  `Todo`, `Deadline` or `Event` each with independent abstract classes (`CompletableTodo`, `CompletableDeadline` and `Repeatable` ).
    * Pros:
        * Design allows the behaviour of `CompletableTodo`, `CompletableDeadline` and `Repeatable` to be extended without (or with minimal) changes to `Todo`, `Deadline` or `Event`.
        * Each `CompletableTodo`, `CompletableDeadline` and `Repeatable` encapsulates all information needed for that specific Task. For example, `Event` which implements `Repeatable` has a specific implementation that allows it to repeat in a specified interval. (Note: The intervals are defined in an `Interval` enumeration.)
        * Design allows `TodoList`, `DeadlineList` and `EventList` to hold specifically and only `CompletableTodo`, `CompletableDeadline` and `Repeatable` respectively. This ensures that implementation errors with respect to how `CompletableTodo`, `CompletableDeadline` and `Repeatable` are stored, can be minimised.

    * Cons:
        * Many classes required.

* **Alternative 2 (implementation used in AB3):** Implement  `Todo`, `Deadline` or `Event` with a common `Task` abstract class.
    * Pros:
        * Easy to implement.
        * Minimal changes needed if a new implementation of `Task` needs to extend behaviors already defined in `Task`.
        * Fewer classes required.
    * Cons:
        * `Task` is not closed to modification. A new implementation of `Task` might require the addition of fields to store additional behaviours and attributes.
        * Risk of having a `Todo` added to a `DeadlineList` is heightened during implementation. This is in contrast to alternative 2, where each `TodoList`, `DeadlineList` and `EventList` holds only `CompletableTodo`, `CompletableDeadline` and `Repeatable` respectively.

### View Projects Feature

This section explains the implementation of the View Project feature. The implementation of other commands that opens panels, windows or tabs are similar.

The `ViewProject` command results in the UI displaying the specified project together with all its related information. 

The mechanism to issue the command to display a new project is facilitated by `ViewProjectUiCommand`, a concrete implementation of the `UiCommand` abstract class, which encapsulates the project `Index` as well as the logic that determines which methods to call in the `MainWindow`.

Given below is an example usage scenario and how the mechanism behaves at each step.

![View Project Sequence Diagram](images/ViewProjectCommandSequenceDiagram.png)

Step 1. The user issues the command `viewP 1` to display a panel containing information about the first project in the project list.

Step 2. A `CommandResult` object is created (see section on [Logic Component](#logic-component)) containing a `ViewProjectUiCommand` object. The `ViewProjectUiCommand` object stores the `Index` of the first project in the project list.

Step 3. The `CommandResult` is passed to the `MainWindow`, which gets the `UiCommand` by calling `CommandResult#getUiCommand()`.

Step 4. The `MainWindow` now calls `UiCommand#execute`, which will result in a call to the overridden method `ViewProjectUiCommand#execute`.

Step5. Execution of this method will result in a call to `MainWindow#selectProject` with the `Index` of the first project as an argument. This will display the first project in the project list.

#### Design Considerations

##### Aspect: How to store and pass around UI related instructions

* **Alternative 1 (current choice):** Encapsulate instructions using `UiCommand` Object.
    * Pros:
        * Design allows behaviour of `UI` to be extended without (or with minimal) changes to the `MainWindow` and `CommandResult`. This makes it relatively easy to add many `UiCommands`.
        * `UiCommand` encapsulates all information needed to execute the instruction (e.g. `Index` of project). It is easy to add new commands that store different types of information.
        * Easy to support complex `UiCommands` that perform multiple instructions or contain logic.

    * Cons:
        * Many classes required.
        * `MainWindow` and `UiCommand` are still highly coupled, as `MainWindow` both invokes the command and performs the requested action. 

* **Alternative 2 (implementation used in AB3):** Store instructions in `CommandResult` as boolean fields.
    * Pros:
        * Easy to implement.
        * Minimal changes needed if the new instruction is a combination of already existing instructions as the already existing boolean fields can be set to true.
        * No need for extra classes.
    * Cons:
        * `MainWindow` and `CommandResult` are not closed to modification. A new instruction to change the UI might require the addition of fields to `CommandResult` (boolean fields for instructions and other fields for related data) as well as a new conditional statement in `MainWindow#execute` to handle the new instruction. This makes it relatively difficult to add new instructions.

### Update Commands [Coming soon in v1.3]

CoLAB has several update commands for projects, events, deadlines, tasks and groupmates. They are used to edit details of entities that have already been created.

Below is a sequence diagram of how an `updateP` command is executed.

![UpdateP command sequence diagram](images/UpdateProjectCommandSequenceDiagram.png)

Step 1. The user types an update project command `updateP 1 n/Group Project`.

Step 2. User input is passed to the `addressBookParser`, which creates a new `UpdateProjectCommand`.

Step 3. The `UpdateProjectCommand` will then be executed by calling its `execute` method.

Step 4. Since the `ModelManager` is passed to `UpdateProjectCommand#execute`, it is able to call a method `ModelManager#setProject` to change an existing project of a given `Index` in the `ProjectsFolder` to the modified version.

Step 5. After the project gets updated, `Model#saveProjectsFolder` is called to save the list of projects to files.

The other update commands require some more work because events, deadlines, tasks and groupmates are sub-components of a project. It is therefore necessary to specify a project in the command so that edits can be applied to that project. Below is a sequence diagram of how an `updateG` (update groupmate) command is executed.

![UpdateP command sequence diagram](images/UpdateGroupmateCommandSequenceDiagram.png)

Step 1. The user types an update project command `updateG 1 n/Alice`.

Step 2. User input is passed to the `addressBookParser`, which creates a new `UpdateGroupmateCommand`.

Step 3. The `UpdateGroupmateCommand` will then be executed by calling its `execute` method.

Step 4. It will then get the list of projects through `Model#getFilteredProjectList`, and use the project `Index` to get the project to be updated.

Step 5. It will then call a method `Project#setGroupmate` to change an existing groupmate of a given `Index` in the `GroupmateList` to the modified version.

Step 5. After the project gets updated, `Model#saveProjectsFolder` is called to save the list of projects to files.

#### Design consideration:

##### Aspect: How the target contact is specified when updating contacts

* **Alternative 1 (current choice):** Pass the `Index` object down to `UniqueContactList#setContact`.
    * Pros: More Consistent in how to pass indexes and locate an element in a `List` throughout the codebase.
    * Cons: Higher coupling since `UniqueContactList` now relies on `Index`.

* **Alternative 2 (implementation used in AB3):** Pass the target `Contact` object to be edited to `UniqueContactList#setContact`.
    * Pros: Lower coupling since `Index` is not a dependency of `UniqueContactList`.
    * Cons: Extra computation of index from the `Contact` object since the index is already provided in the command. Passing the original project around does not provide more information than passing only the index.

* **Alternative 3:** Pass the zero-based index as an integer down to `UniqueContactList#setContact`.
    * Pros: Will use less memory (only needs memory for an integer instead of a `Contact` object or an `Index` object), no reliance on `Index`.
    * Cons: May be confusing for new developers since some other parts of the code use one-based indexes instead.

### Add Event to Project Command [Implemented in v1.2]

The mechanism is used to add an event to the `EventList` of `Project` specified by the index in the project list shown.

The client creates a concrete `AddEventCommand` that contains the specified index of project and a valid Event object. Each concrete `AddEventCommand` implements the `AddEventCommand#execute` method, which calls the appropriate method(s) in `Project` to update its `EventList` and appropriate method(s) in `Model` to update the Project List.

Given below is an example usage scenario and how the mechanism behaves at each step.

Step 1. The user executes the command `addEto 1 d/Tutorial i/WEEKLY at/25-03-2021`, which adds an `Event` with description, interval and date specified to `Project` 1 in Project List.

Step 2: The input is parsed by `AddEventCommandParser`. It checks if `Event` provided is valid or not. If input is invalid, an exception will be throw and `Ui` will help print out the exception message. Otherwise, an `AddEventCommand` will be created.

Step 3: The `AddEventCommand#execute` is called. It checks whether `Index` provided is valid or not and if `Event` provided is duplicated. If check fails, an exception will be thrown, `Ui` will help print out the exception message. Otherwise, the change will be made to `Project`and `Model` in the next step.

Step 4: The `Project` specified by Index will call addEvent function to add the given `Event` to its `EventList`. `Model` updates its Project List based on the change.

Step 5: A `CommandResult` object is created (see section on [Logic Component](#logic-component)) containing the Event added. The `Ui` will help print out the success message.

#### Design Considerations

##### Aspect: How to add a new `Event` to a `Project`.

* **Alternative 1 (current choice):** `Project` tells its `EventList` to update the list of Events stored.
    * Pros:
        * This implementation requires no additional time and space (for creation of new 'Project` and `EventList` object).
    * Cons:
        * This implementation will not work with an immutable implementation of `EventList`

* **Alternative 2:** A new `Project` object is initialized with a new `EventList` object containing the added `Event`.
    * Pros: 
        * If the implementation of `EventList` becomes immutable. This implementaion still works.
    * Cons:
        * This implementation requires more time and space (for creation of new 'Project` and `EventList` object).

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th contact in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new contact. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the contact was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

![CommitActivityDiagram](images/CommitActivityDiagram.png)

#### Design consideration:

##### Aspect: How undo & redo executes

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the contact being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* student currently enrolled in a university
* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:

* supports only features a university student needs without additional clutter
* information organised by categories relevant to university students (e.g. tag by modules)
* manage contacts faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                 | I want to …​                | So that I can…​                                                     |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | new user                                   | see usage instructions         | refer to instructions when I forget how to use the App                 |
| `* * *`  | user                                       | add a new contact               | keep track of details from peers I have crossed paths with             |
| `* * *`  | user                                       | edit a contact's details        | update their details when there is change                              |
| `* * *`  | user                                       | delete a contact                | remove entries that I no longer need                                   |
| `* * *`  | user                                       | find a contact by name          | locate details of contacts without having to go through the entire list |
| `* * *`  | user                                       | tag a contact with tags         | easily keep track of who the contact is                                |
| `* *`    | University Student                         | find contacts by modules taken | easily find contacts who take the same module as me                    |
| `* *`    | Student Teaching Assistant                 | find contacts by tutorial group| easily find contacts of students in my class                           |
| `* *`    | user                                       | purge all data in the app      | start my address book from fresh                                       |
| `*`      | long time user                             | archive data files             | refer to old address books when needed                                 |


*{More to be added}*

### Use cases

(For all use cases below, the **System** is `CoLAB` and the **Actor** is the `user`, unless specified otherwise)

<ul id="use-cases-toc"></ul>
<script>
document.addEventListener('DOMContentLoaded', (event) => {
  const usecase_h4s = Array.prototype.slice.call(document.getElementsByTagName("h4")).filter(h => h.textContent.startsWith('UC'));
  for (const usecase_h4 of usecase_h4s) {
      const usecase_text = usecase_h4.textContent;
      const usecase_link = '#' + usecase_h4.getAttribute('id');
      const li = document.createElement('li');
      li.innerHTML = `<a href="${usecase_link}">${usecase_text}</a>`;
      document.getElementById('use-cases-toc').appendChild(li);
  }
});
</script>

#### UC1 - Add a project

**MSS**

1. User requests to add a project.
2. CoLAB adds the project.

   Use case ends.

**Extensions**

* 1a. The given name argument is invalid.

    * 1a1. CoLAB shows an error message.

      Use case resumes at step 1.

* 2a. User decides to undo the add action.

    * 2a1. CoLAB reverses the effects of the previous command.

      Use case ends.

#### UC2 - Delete a project

**MSS**

1. User requests to delete a specific project in the list.
2. CoLAB deletes the contact.

   Use case ends.

**Extensions**

* 1a. The given project index is invalid.

    * 1a1. CoLAB shows an error message.

      Use case resumes at step 1.

* 2a. User decides to undo the delete action.

    * 2a1. CoLAB reverses the effects of the previous command.

      Use case ends.

#### UC3 - Add or modify information about a project

**MSS**

1. User requests to edit information about a project.
2. CoLAB updates entry with new information.

   Use case ends.

**Extensions**

* 1a. The given arguments are invalid.

    * 1a1. CoLAB shows an error message.

      Use case resumes at step 1.

* 2a. User decides to undo the update action.

    * 2a1. CoLAB reverses the effects of the previous command.

      Use case ends.

#### UC4 - Add a todo to a project

**MSS**

1. User switches to the project panel of a specific project.
2. User lists all todos under the project.
3. User requests to add a todo to the project.
4. CoLAB adds the project.

   Use case ends.

**Extensions**

* 1a. The given project index is invalid.

    * 1a1. CoLAB shows an error message.

      Use case resumes at step 1.

* 3a. The given arguments are invalid.

    * 2a1. CoLAB shows an error message.

      Use case resumes at step 3.

* 4a. User decides to undo the add action.

    * 4a1. CoLAB reverses the effects of the previous command.

      Use case ends.

#### UC5 - Delete a todo from a project

**MSS**

1. User switches to the project panel of a specific project.
2. User lists all todos under the project.
3. User requests to delete a specific todo in the list.
4. CoLAB deletes the todo.

   Use case ends.

**Extensions**

* 1a. The given project index is invalid.

    * 1a1. CoLAB shows an error message.

      Use case resumes at step 1.

* 2a. The list of todos is empty.

  Use case ends.

* 3a. The given todo index is invalid.

    * 3a1. CoLAB shows an error message.

      Use case resumes at step 3.

* 4a. User decides to undo the delete action.

    * 4a1. CoLAB reverses the effects of the previous command.

      Use case ends.

#### UC6 - Add or modify information about a todo in a project

**MSS**

1. User switches to the project panel of a specific project.
2. User lists all todos under the project.
3. User requests to edit information about a todo.
4. CoLAB updates entry with new information.

   Use case ends.

**Extensions**

* 1a. The given project index is invalid.

    * 1a1. CoLAB shows an error message.

      Use case resumes at step 1.

* 2a. The list of todos is empty.

  Use case ends.

* 3a. The given arguments are invalid.

    * 3a1. CoLAB shows an error message.

      Use case resumes at step 3.

* 4a. User decides to undo the update action.

    * 4a1. CoLAB reverses the effects of the previous command.

      Use case ends.

#### UC7 - Add a contact

**MSS**

1. User requests to add a contact.
2. CoLAB adds the contact.

   Use case ends.

**Extensions**

* 1a. The given arguments are invalid.

    * 1a1. CoLAB shows an error message.

      Use case resumes at step 1.

* 2a. User decides to undo the add action.

    * 2a1. CoLAB reverses the effects of the previous command.

      Use case ends.

#### UC8 - Find a specific contact

**MSS**

1. User requests to find a contact.
2. CoLAB shows a list of contacts that match user's query.

   Use case ends.

**Extensions**

* 2a. The list of contacts is empty.

  Use case ends.

#### UC9 - Delete a contact

**MSS**

1. User requests to list contacts.
2. CoLAB shows a list of contacts.
3. User requests to delete a specific contact in the list.
4. CoLAB deletes the contact.

   Use case ends.

**Extensions**

* 2a. The list of contacts is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. CoLAB shows an error message.

      Use case resumes at step 2.

* 4a. User decides to undo the delete action.

    * 4a1. CoLAB reverses the effects of the previous command.

      Use case ends.

#### UC10 - Add or Modify information about a contact

**MSS**

1. User requests to edit information about a contact.
2. CoLAB updates entry with new information.

   Use case ends.

**Extensions**

* 1a. The given arguments are invalid.

    * 1a1. CoLAB shows an error message.

      Use case resumes at step 1.

* 2a. User decides to undo the update action.

    * 2a1. CoLAB reverses the effects of the previous command.

      Use case ends.

#### UC30 - Purge all entries from the app

**MSS**

1. User requests to delete all entries from the app.
2. CoLAB deletes all data from the app.

   Use case ends.

**Extensions**

* 2a. User decides to undo the purge action.

    * 2a1. CoLAB reverses the effects of the previous command.

      Use case ends.

#### Checklist

* Use period.
* Correct numbering.
* Working links.

### Non-Functional Requirements

* Technical requirements:
    * CoLAB should work on any _mainstream OS_ on both 32-bit and 64-bit architectures as long as it has Java `11` or above installed.
    * CoLAB should work under _common screen resolutions_. (i.e. the window should not be out of boundary)
* Performance requirements:
    * CoLAB should be able to hold _up to 1000 contacts_ and _1000 projects_ without a noticeable sluggishness in performance for typical usage.
    * The response to any command should be shown _within 1 second_.
* Constraints:
    * CoLAB should be _backward compatible_ with data files produced by earlier versions as much as possible. If one release is not compatible with earlier versions, a migration guide should be provided.
    * CoLAB must be open source under [the MIT License](https://raw.githubusercontent.com/AY2021S2-CS2103T-T11-2/tp/master/LICENSE).
* Quality requirements:
    * A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
    * A user familiar with CLI tools should find CoLAB commands very intuitive.
    * A user who has no experience with CLI tools should be able to find CoLAB easy to use with the help of the [_User Guide_](UserGuide.md).
* Process requirements:
    * the project is expected to adhere to a schedule that delivers a feature set every two weeks.

### Glossary

* **Common screen resolutions**: minimum _640×480_, maximum _3840×2160_
* **Mainstream OS**: Windows, Linux, Unix, macOS
* **MSS**: Main Success Scenario
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a contact

1. Deleting a contact while all contacts are being shown

    1. Prerequisites: List all contacts using the `list` command. Multiple contacts in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No contact is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
