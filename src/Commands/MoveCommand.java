package Commands;

import lejos.nxt.NXTMotor;
import settings.Settings;

/**
 * Command for turning two NXT motors with individual power values for a certain time window
 */
public class MoveCommand extends AbstractCommand {
    private NXTMotor left;
    private NXTMotor right;
    private int powerLeft;
    private int powerRight;
    private int stepSize;


    /**
     * Full constructor
     * @param left
     * @param right
     * @param powerLeft
     * @param powerRight
     * @param stepSize
     */
    public MoveCommand(NXTMotor left, NXTMotor right, int powerLeft, int powerRight, int stepSize) {
        this.left = left;
        this.right = right;
        this.powerLeft = powerLeft;
        this.powerRight = powerRight;
        this.stepSize = stepSize;
    }

    public MoveCommand(int powerLeft, int powerRight, int stepSize) {
        this(Settings.mLeft, Settings.mRight, powerLeft, powerRight, stepSize);
    }

    public MoveCommand(int power, int stepSize) {
        this(power, power, stepSize);
    }

    public MoveCommand(int stepSize) {
        this(Settings.defaultPower, stepSize);
    }

    /**
     * Sets the individual power levels for each motor for the given time window
     * @throws InterruptedException
     */
    @Override
    public void action() throws InterruptedException {
        left.setPower(powerLeft);
        right.setPower(powerRight);
        Thread.sleep(stepSize);
        left.setPower(0);
        right.setPower(0);
    }

    /**
     * Reverts power values by multiplication with -1
     */
    @Override
    public AbstractCommand backwards() {
        return new MoveCommand(left, right, -powerLeft, -powerRight, stepSize);
    }

    /**
     * Inverts power values by swapping original values
     */
    @Override
    public AbstractCommand inverse() {
        return new MoveCommand(left, right, powerRight, powerLeft, stepSize);
    }

    @Override
    public AbstractCommand copy() {
        return new MoveCommand(left, right, powerLeft, powerRight, stepSize);
    }
}
