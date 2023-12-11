package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandSequence extends AbstractCommand {
    List<AbstractCommand> sequence;

    public CommandSequence(AbstractCommand... commands) {
        this.sequence = Arrays.asList(commands);
    }

    public CommandSequence(List<AbstractCommand> sequence) {
        this.sequence = sequence;
    }

    @Override
    void action() throws InterruptedException {
        for (AbstractCommand abstractCommand : sequence) {
            abstractCommand.action();
        }
    }

    @Override
    AbstractCommand inverse() {
        List<AbstractCommand> reversed = new ArrayList<>();
        sequence.forEach(p -> reversed.add(0, p));
        reversed.forEach(AbstractCommand::inverse);
        return new CommandSequence(reversed);
    }

    @Override
    AbstractCommand sequenceInverse() {
        List<AbstractCommand> reversed = new ArrayList<>();
        sequence.forEach(p -> reversed.add(0, p));
        reversed.forEach(AbstractCommand::sequenceInverse);
        return new CommandSequence(reversed);
    }
}
