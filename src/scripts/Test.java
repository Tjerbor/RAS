package scripts;

import commands.CommandSequence;
import commands.MoveCommand;
import commands.TurnByDegreesCommand;
import lejos.nxt.Button;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Button.waitForAnyPress();
        CommandSequence corner = new CommandSequence(
                new MoveCommand(2000),
                new TurnByDegreesCommand(90),
                new MoveCommand(000)
        );
        corner.action();

        /*CommandSequence history = new CommandSequence();
        CommandSequence corner = new CommandSequence(
                new MoveCommand(mLeft, mRight, defaultPower, 2000),
                new TurnByDegreesCommand(mLeft, mRight, compass, 90, 15,defaultPower,50)
        );
        history.add(corner.copy());
        history.add(corner.copy());
        history.add(corner.copy());
        history.add(corner.copy());
        Button.waitForAnyPress();
        history.action();
        (new TurnByDegreesCommand(mLeft, mRight, compass, 180, defaultPower)).action();
        AbstractCommand reverse = history.inverse();
        reverse.action();

         */
    }
}
