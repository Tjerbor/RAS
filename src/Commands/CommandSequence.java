package Commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Command for multi-command execution
 * Used as command history
 */
public class CommandSequence extends AbstractCommand implements Iterable<AbstractCommand> {
    List<AbstractCommand> sequence;

    public CommandSequence(AbstractCommand... commands) {
        this.sequence = new ArrayList<>();
        for (AbstractCommand c : commands) {
            sequence.add(c);
        }
    }

    public CommandSequence(List<AbstractCommand> sequence) {
        this.sequence = sequence;
    }

    /**
     * Executes every stored command in the same order they have been added
     * @throws InterruptedException
     */
    @Override
    public void action() throws InterruptedException {
        for (AbstractCommand abstractCommand : sequence) {
            abstractCommand.action();
        }
    }

    /**
     * Returns a sequence of every stored command reverted and reverts every stored command
     */
    @Override
    public AbstractCommand backwards() {
        List<AbstractCommand> reversed = new ArrayList<>();
        for (AbstractCommand c : sequence) {
            reversed.add(0, c.backwards());
        }
        return new CommandSequence(reversed);
    }

    /**
     *R eturns a sequence of every stored command reverted and inverts every stored command
     */
    @Override
    public AbstractCommand inverse() {
        List<AbstractCommand> reversed = new ArrayList<>();
        for (AbstractCommand c : sequence) {
            reversed.add(0, c.inverse());
        }
        return new CommandSequence(reversed);
    }

    /**
     * Method for adding additional commands
     * @param commands
     */
    public void add(AbstractCommand... commands) {
        for (AbstractCommand c : commands) {
            sequence.add(c);
        }
    }

    @Override
    public Iterator<AbstractCommand> iterator() {
        return sequence.iterator();
    }

    @Override
    public AbstractCommand copy() {
        List<AbstractCommand> copy = new ArrayList<>();
        for (AbstractCommand c : sequence) {
            copy.add(c);
        }
        return new CommandSequence(copy);
    }
}
