package seedu.address.model.project;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.CompletableTodo;
import seedu.address.model.task.todo.Todo;

/**
 * Represents a list of Todos.
 */
public class TodoList {

    private final ObservableList<CompletableTodo> todos = FXCollections.observableArrayList();

    /**
     * Constructs a empty {@code TodoList}.
     */
    public TodoList() {}

    /**
     * Constructs a {@code TodoList}.
     *
     * @param todos A list of {@code Todos}.
     */
    public TodoList(List<CompletableTodo> todos) {
        requireNonNull(todos);

        this.todos.addAll(todos);
    }

    /**
     * Adds a todo to this {@code TodoList}.
     *
     * @param todo {@code Todo} to add.
     */
    public void addTodo(Todo todo) {
        requireNonNull(todo);
        this.todos.add(todo);
    }

    /**
     * Returns {@code TodoList} as an {@code ObservableList<CompletableTodo>}
     *
     * @return An {@code ObservableList<CompletableTodo>}
     */
    public ObservableList<CompletableTodo> getTodos() {
        return this.todos;
    }

    /**
     * Deletes an todo from this {@code TodoList}.
     *
     * @param i Index of {@code Todo} to be deleted.
     */
    public void deleteTodo(Integer i) {
        requireNonNull(i);
        this.todos.remove(i);
    }

    /**
     * Returns a copy of this {@code TodoList}
     * @return A copy of this {@code TodoList}
     */
    public TodoList getCopy() {
        return new TodoList(getTodos());
    }

    /**
     * Returns a sequential stream with this {@code TodoList} as its source.
     *
     * @return a sequential Stream over the Todos in this {@code TodoList}.
     */
    public Stream<CompletableTodo> stream() {
        return this.todos.stream();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoList // instanceof handles nulls
                && this.todos.equals(((TodoList) other).todos)); // state check
    }

    @Override
    public int hashCode() {
        return todos.hashCode();
    }

}
