package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProjects.getTypicalProjectsFolder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListProjectsCommand.
 */
public class ListProjectsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalProjectsFolder(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getProjectsFolder(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListProjectsCommand(), model, ListProjectsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showProjectAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListProjectsCommand(), model, ListProjectsCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
