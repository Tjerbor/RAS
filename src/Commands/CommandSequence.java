package Commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    @Override
    public void action(boolean waitExtraTime) throws InterruptedException {
        for (AbstractCommand abstractCommand : sequence) {
            abstractCommand.action(waitExtraTime);
        }
    }

    @Override
    public AbstractCommand backwards() {
        List<AbstractCommand> reversed = new ArrayList<>();
        for (AbstractCommand c : sequence) {
            reversed.add(0, c.backwards());
        }
        return new CommandSequence(reversed);
    }

    @Override
    public AbstractCommand inverse() {
        List<AbstractCommand> reversed = new ArrayList<>();
        for (AbstractCommand c : sequence) {
            reversed.add(0, c.inverse());
        }
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
        for (AbstractCommand c : sequence) {
            copy.add(c);
        }
        return new CommandSequence(copy);
    }
}
