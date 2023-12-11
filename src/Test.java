import Commands.AbstractCommand;
import Commands.CommandSequence;
import Commands.MoveCommand;
import Commands.TurnByDegreesCommand;
import lejos.nxt.*;
import lejos.nxt.addon.CompassHTSensor;

public class Test {
    private static final NXTMotor mRight = new NXTMotor(MotorPort.A);
    private static final NXTMotor mLeft = new NXTMotor(MotorPort.C);
    private static final CompassHTSensor compass = new CompassHTSensor(SensorPort.S1);
    static int defaultPower = 25;

    private static final double error_epsilon = 10.0;

    public static void main(String[] args) throws InterruptedException {
        Button.waitForAnyPress();
        CommandSequence corner = new CommandSequence(
                new MoveCommand(mLeft, mRight, defaultPower, 2000),
                new TurnByDegreesCommand(mLeft, mRight, compass, 90, 15, defaultPower, 50),
                new MoveCommand(mLeft, mRight, defaultPower, 2000)
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
