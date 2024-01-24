package Commands;

import lejos.nxt.NXTMotor;
import lejos.nxt.addon.CompassHTSensor;
import settings.Settings;

/**
 * Command for turning the robot a certain amount of degrees
 * Based on average turn time for the robot
 */
public class TurnByDegreesCommand extends AbstractCommand {
    private NXTMotor left;
    private NXTMotor right;
    /**
     * Deprecated
     */
    private CompassHTSensor compass;
    private int degrees;
    /**
     * Deprecated
     */
    private int error_epsilon;
    private int defaultPower;
    private int stepSize;

    /**
     * Full constructor
     * @param left
     * @param right
     * @param compass
     * @param degrees
     * @param error_epsilon
     * @param defaultPower
     * @param stepSize
     */
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

    /**
     * Turns the robot
     * Positive degrees for clockwise rotation
     * Negative degrees for counter-clock wise rotation
     *
     * Based on the robot turning 13,75 times per minute
     * if one wheels has the power of 25 and the other -25
     * @throws InterruptedException
     */
    @Override
    public void action() throws InterruptedException {
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

    /**
     * Reverts by flipping rotation direction and inverting the wheel's power
     * @return
     */
    @Override
    public AbstractCommand backwards() {
        return new TurnByDegreesCommand(left, right, compass, -degrees, error_epsilon, -defaultPower, stepSize);
    }

    /**
     * Inverts by flipping rotation direction
     * @return
     */
    @Override
    public AbstractCommand inverse() {
        return new TurnByDegreesCommand(left, right, compass, -degrees, error_epsilon, defaultPower, stepSize);
    }

    @Override
    public AbstractCommand copy() {
        return new TurnByDegreesCommand(left, right, compass, degrees, error_epsilon, defaultPower, stepSize);
    }
}
