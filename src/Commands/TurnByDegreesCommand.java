package Commands;

import lejos.nxt.NXTMotor;
import lejos.nxt.addon.CompassHTSensor;
import settings.Settings;

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

    public TurnByDegreesCommand(int degrees, int error_epsilon, int defaultPower) {
        this(Settings.mLeft, Settings.mRight, Settings.compass, degrees, error_epsilon, defaultPower, 100);
    }

    public TurnByDegreesCommand(int degrees) {
        this(degrees, Settings.error_epsilon, 25);
    }

    @Override
    public void action(boolean waitExtraTime) throws InterruptedException {
        if (degrees > 0) {
            left.setPower(defaultPower);
            right.setPower(-defaultPower);
        } else {
            left.setPower(-defaultPower);
            right.setPower(defaultPower);
            degrees = -degrees;
        }
        //13,75 Umdrehungen pro Minute bei Power (l:25,r:-25)
        Thread.sleep((int) ((degrees / 360.0) * (60000.0 / 13.75)));
        left.setPower(0);
        right.setPower(0);
    }

    @Override
    public AbstractCommand backwards() {
        return new TurnByDegreesCommand(left, right, compass, -degrees, error_epsilon, -defaultPower, stepSize);
    }

    @Override
    public AbstractCommand inverse() {
        return new TurnByDegreesCommand(left, right, compass, -degrees, error_epsilon, defaultPower, stepSize);
    }

    @Override
    public AbstractCommand copy() {
        return new TurnByDegreesCommand(left, right, compass, degrees, error_epsilon, defaultPower, stepSize);
    }
}
