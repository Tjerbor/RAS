package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CommandSequence extends AbstractCommand implements Iterable<AbstractCommand> {
    List<AbstractCommand> sequence;

    public CommandSequence(AbstractCommand... commands) {
        if (commands.length > 0) {
            this.sequence = Arrays.asList(commands);
        } else {
            this.sequence = new ArrayList<>();
        }
    }

    public CommandSequence(List<AbstractCommand> sequence) {
        this.sequence = sequence;
    }

    @Override
    public void action() throws InterruptedException {
        for (AbstractCommand abstractCommand : sequence) {
            abstractCommand.action();
        }
    }

    @Override
    public AbstractCommand backwards() {
        List<AbstractCommand> reversed = new ArrayList<>();
        sequence.forEach(p -> reversed.add(0, p));
        reversed.forEach(AbstractCommand::backwards);
        return new CommandSequence(reversed);
    }

    @Override
    public AbstractCommand inverse() {
        List<AbstractCommand> reversed = new ArrayList<>();
        sequence.forEach(p -> reversed.add(0, p));
        reversed.forEach(AbstractCommand::inverse);
        return new CommandSequence(reversed);
    }

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
        sequence.forEach(p -> copy.add(p));
        return new CommandSequence(copy);
    }
}
