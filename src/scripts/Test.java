package scripts;

import Commands.CommandSequence;
import Commands.MoveCommand;
import Commands.TurnByDegreesCommand;
import lejos.nxt.Button;

/**
 * Test class
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        CommandSequence corner = new CommandSequence(
                new MoveCommand(3000),
                new TurnByDegreesCommand(90)
        );

        CommandSequence history = new CommandSequence();
        history.add(corner.copy());
        history.add(corner.copy());
        history.add(corner.copy());
        history.add(corner.copy());
        Button.waitForAnyPress();
        history.action();
        Button.waitForAnyPress();
        (new TurnByDegreesCommand(180)).action();
        (history.inverse()).action();

    }
}
