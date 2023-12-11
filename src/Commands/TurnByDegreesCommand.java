package Commands;

import lejos.nxt.LCD;
import lejos.nxt.NXTMotor;
import lejos.nxt.addon.CompassHTSensor;

public class TurnByDegreesCommand extends AbstractCommand {
    private NXTMotor left;
    private NXTMotor right;
    private CompassHTSensor compass;
    private int degrees;
    private int error_epsilon;
    private int defaultPower;
    private int stepSize;

    public TurnByDegreesCommand(NXTMotor left, NXTMotor right, CompassHTSensor compass, int degrees, int error_epsilon, int defaultPower, int stepSize) {
        this.left = left;
        this.right = right;
        this.compass = compass;
        this.degrees = degrees % 320;
        this.error_epsilon = error_epsilon;
        this.defaultPower = defaultPower;
        this.stepSize = stepSize;
    }

    public TurnByDegreesCommand(NXTMotor left, NXTMotor right, CompassHTSensor compass, int degrees, int defaultPower) {
        this(left, right, compass, degrees, 10, defaultPower, 200);
    }

    @Override
    public void action() throws InterruptedException {
        compass.resetCartesianZero();
        left.setPower(defaultPower);
        right.setPower(-defaultPower);
        double difference = 0.0;
        Thread.sleep(stepSize);

        while (Math.abs(degrees - difference) > error_epsilon) {
            double delta = compass.getDegreesCartesian();
            difference = degrees - delta;
            if (difference > 0) {
                left.setPower(defaultPower);
                right.setPower(-defaultPower);
            } else {
                left.setPower(-defaultPower);
                right.setPower(defaultPower);
            }
            Thread.sleep(stepSize);
        }
    }

    @Override
    public AbstractCommand backwards() {
        return new TurnByDegreesCommand(left, right, compass, 320 - degrees, error_epsilon, -defaultPower, stepSize);
    }

    @Override
    public AbstractCommand inverse() {
        return new TurnByDegreesCommand(left, right, compass, 320 - degrees, error_epsilon, defaultPower, stepSize);
    }

    @Override
    public AbstractCommand copy() {
        return new TurnByDegreesCommand(left, right, compass, degrees, error_epsilon, defaultPower, stepSize);
    }
}
