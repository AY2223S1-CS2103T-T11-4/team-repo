package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.interest.Interest;
import seedu.address.model.student.Student;

/**
 * Adds a list of interests to the specified student.
 */
public class AddInterestCommand extends Command {

    public static final String COMMAND_WORD = "addInt";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds interest(s) to the batchmate identified by the index number used in the displayed student list.\n"
            + "Parameters: INDEX (must be a positive integer) INTEREST [MORE_INTERESTS]...\n"
            + "Example: " + COMMAND_WORD + " 1 tennis netflix";
    public static final String MESSAGE_SUCCESS = "Interest(s) added successfully!";

    private final Index targetIndex;
    private final Set<Interest> interests;

    /**
     * Constructs a command that adds all interests specified to the target batchmate.
     *
     * @param index The index of the batchmate.
     * @param interests The set of interests to be marked.
     */
    public AddInterestCommand(Index index, Set<Interest> interests) {
        requireNonNull(index);
        requireNonNull(interests);

        this.targetIndex = index;
        this.interests = interests;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Student studentToEdit = lastShownList.get(targetIndex.getZeroBased());
        studentToEdit.addInterests(interests);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, studentToEdit), false, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddInterestCommand)) {
            return false;
        }

        // state check
        AddInterestCommand e = (AddInterestCommand) other;
        return targetIndex.equals(e.targetIndex)
                && interests.equals(e.interests);
    }
}
