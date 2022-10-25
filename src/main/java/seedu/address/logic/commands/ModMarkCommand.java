package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Mod;
import seedu.address.model.person.Person;

/**
 * Marks mods of the batchmate specified as taken.
 */
public class ModMarkCommand extends ModCommand {

    public static final String COMMAND_WORD = "mark";
    public static final String MESSAGE_SUCCESS = "Successfully marked the specified mods.";
    public static final String MESSAGE_INVALID_MOD = "This batchmate is not taking all of the modules specified."
            + "\nPlease check the list of mods and try again.";
    private final Index targetIndex;
    private final ObservableList<Mod> mods;

    /**
     * Constructs a command that marks all mods specified at the target batchmate as taken.
     *
     * @param index The index of the batchmate.
     * @param mods The set of mods to be marked.
     */
    public ModMarkCommand(Index index, ObservableList<Mod> mods) {
        requireNonNull(index);
        requireNonNull(mods);

        this.targetIndex = index;
        this.mods = mods;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display.
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        if (personToEdit.canEditMods(mods)) {
            personToEdit.markMods(mods);
        } else {
            throw new CommandException(MESSAGE_INVALID_MOD);
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, personToEdit),
                false,
                false,
                false,
                false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModMarkCommand)) {
            return false;
        }

        // state check
        ModMarkCommand e = (ModMarkCommand) other;
        return targetIndex.equals(e.targetIndex)
                && mods.equals(e.mods);
    }
}
