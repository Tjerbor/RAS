import Commands.AbstractCommand;
import Commands.CommandSequence;
import Commands.MoveCommand;
import Commands.TurnByDegreesCommand;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;

public class Test {
    private static final NXTMotor mRight = new NXTMotor(MotorPort.A);
    private static final NXTMotor mLeft = new NXTMotor(MotorPort.C);
    private static final CompassHTSensor compass = new CompassHTSensor(SensorPort.S2);
    static int defaultPower = 25;

    private static final double error_epsilon = 10.0;

    public static void main(String[] args) throws InterruptedException {
        /*CommandSequence history = new CommandSequence();
        CommandSequence corner = new CommandSequence(
                new MoveCommand(mLeft, mRight, defaultPower, 2000),
                new TurnByDegreesCommand(mLeft, mRight, compass, 90, defaultPower)
        );
        for (int i = 0; i < 4; i++) {
            CommandSequence current = corner.

        }
        history.action();
        (new TurnByDegreesCommand(mLeft, mRight, compass, 90, defaultPower)).action();
        AbstractCommand reverse = history.inverse();
        reverse.action();

         */
        ////

    }
}
