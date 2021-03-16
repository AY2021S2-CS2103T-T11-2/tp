package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.ui.UiCommand;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private final Index indexOfProject;

    /** Information on which UI command to excecute **/
    private final UiCommand uiCommand;

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, {@code uiCommand} and
     * {@code indexOfProject}.
     */
    public CommandResult(String feedbackToUser, UiCommand uiCommand, Index indexOfProject) {
        requireAllNonNull(feedbackToUser, uiCommand, indexOfProject);
        this.feedbackToUser = feedbackToUser;
        this.uiCommand = uiCommand;
        this.indexOfProject = indexOfProject;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code uiCommand}.
     * All other fields are set to their default value.
     */
    public CommandResult (String feedbackToUser, UiCommand uiCommand) {
        requireAllNonNull(feedbackToUser, uiCommand);
        this.feedbackToUser = feedbackToUser;
        this.uiCommand = uiCommand;
        this.indexOfProject = null;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}.
     * All other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        this.feedbackToUser = feedbackToUser;
        this.uiCommand = UiCommand.NONE;
        this.indexOfProject = null;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public UiCommand getUiCommand() {
        return uiCommand;
    }

    public Index getIndexOfProject() {
        return indexOfProject;
    }

    /**
     * Returns true if there is a UiCommand.
     * @return true if UiCommand is not {@code UiCommand.NONE}
     */
    public boolean hasUiCommand() {
        return getUiCommand() != UiCommand.NONE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && uiCommand == otherCommandResult.uiCommand;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, uiCommand);
    }

}
