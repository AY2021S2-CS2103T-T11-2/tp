package seedu.address.model.project;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.project.exceptions.DuplicateDeadlineException;
import seedu.address.model.task.CompletableDeadline;
import seedu.address.model.task.deadline.Deadline;

/**
 * Represents a list of Deadlines.
 */
public class DeadlineList {

    private final ObservableList<CompletableDeadline> deadlines = FXCollections.observableArrayList();

    /**
     * Constructs a empty {@code DeadlineList}.
     */
    public DeadlineList() {}

    /**
     * Constructs a {@code DeadlineList}.
     *
     * @param deadlines A list of {@code deadlines}.
     */
    public DeadlineList(List<CompletableDeadline> deadlines) {
        requireNonNull(deadlines);

        this.deadlines.addAll(deadlines);
    }

    /**
     * Adds a deadline to this {@code DeadlineList}.The {@code Deadline} must not already exist in
     * the {@code DeadlineList}.
     *
     * @param deadline {@code Deadline} to add.
     */
    public void addDeadline(Deadline deadline) {
        requireNonNull(deadline);

        if (hasDeadline(deadline)) {
            throw new DuplicateDeadlineException();
        }

        this.deadlines.add(deadline);
    }

    /**
     * Set the {@code Deadline} specified by index with a new {@code Deadline}.
     *
     * @param index index specifies the target {@code Deadline}.
     * @param deadline new {@code Deadline} for this index.
     */
    public void setDeadline(Integer index, CompletableDeadline deadline) {
        requireNonNull(deadline);

        this.deadlines.set(index, deadline);
    }

    /**
     * Returns {@code deadlines} as an {@code ObservableList<CompletableDeadline>}
     *
     * @return An {@code ObservableList<CompletableDeadline>}
     */
    public ObservableList<CompletableDeadline> getDeadlines() {
        return this.deadlines;
    }

    /**
     * Deletes a deadline from this {@code DeadlineList}
     * .
     * @param i Index of {@code Deadline} to be deleted.
     */
    public void deleteDeadline(Integer i) {
        requireNonNull(i);
        this.deadlines.remove((int) i);
    }

    /**
     * Marks a deadline from this {@code DeadlineList} as done.
     *
     * @param i Index of {@code Deadline} to be marked as done.
     */
    public void markAsDone(Integer i) {
        requireNonNull(i);
        CompletableDeadline deadline = deadlines.get(i);
        deadline.markAsDone();

        // Force observable list to update
        this.deadlines.set(i, deadline);
    }

    /**
     * Returns a copy of this {@code DeadLineList}
     *
     * @return A copy of this {@code DeadlineList}
     */
    public DeadlineList getCopy() {
        return new DeadlineList(getDeadlines());
    }

    /**
     * Returns all {@code CompletableDeadline} that fall on a specific {@code LocalDate}
     *
     * @param dateOfEvent The {@code LocalDate} which the deadlines occur on.
     * @return A {@code FilteredList<CompletableDeadline>}
     */
    public FilteredList<CompletableDeadline> getDeadlinesOnDate(LocalDate dateOfEvent) {
        requireNonNull(dateOfEvent);
        Predicate<CompletableDeadline> predicate = deadline -> deadline.getBy().isEqual(dateOfEvent);
        return deadlines.filtered(predicate);
    }

    /**
     * Returns a sequential stream with this {@code DeadlineList} as its source.
     *
     * @return a sequential Stream over the completables in this {@code DeadlineList}.
     */
    public Stream<CompletableDeadline> stream() {
        return deadlines.stream();
    }

    /**
     * Checks if the {@code DeadlineList} already contains the specified {@code deadlineToCheck}.
     *
     * @param deadlineToCheck The deadline that is to be checked.
     * @return true if this project contains the specified deadline, false otherwise.
     */
    public boolean hasDeadline(CompletableDeadline deadlineToCheck) {
        for (CompletableDeadline deadline: deadlines) {
            if (deadlineToCheck.equals(deadline)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a {@code Deadline} in the {@code DeadlineList} done or not.
     *
     * @param index index of that {@code Deadline}.
     * @return true if that {@code Deadline} is done, false otherwise.
     */
    public boolean checkIsDone(Integer index) {
        return deadlines.get(index).getIsDone();
    }

    /**
     * Returns the size of the {@code DeadlineList}.
     *
     * @return size of the {@code DeadlineList}.
     */
    public int size() {
        return deadlines.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineList // instanceof handles nulls
                && deadlines.equals(((DeadlineList) other).deadlines)); // state check
    }

    @Override
    public int hashCode() {
        return deadlines.hashCode();
    }
}
