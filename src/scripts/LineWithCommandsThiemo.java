package scripts;

import Commands.AbstractCommand;
import Commands.CommandSequence;
import Commands.MoveCommand;
import Commands.TurnByDegreesCommand;
import settings.Settings;
import lejos.nxt.*;

public class LineWithCommandsThiemo {
    private static final LightSensor ldRight = Settings.ldRight;
    private static final LightSensor ldLeft = Settings.ldLeft;
    private static final NXTMotor mRight = Settings.mRight;
    private static final NXTMotor mLeft = Settings.mLeft;
    private static int defaultPower = Settings.defaultPower;
    private static int sleep = 50;

    /**
     * History stack of executed NXT commands
     */
    private static CommandSequence history = new CommandSequence();

    /**
     * Final line follower and backtracking script
     * Follows line for 20 seconds
     * Subsequently turns 180 degrees and begins backtracking
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        LCD.drawString("Start the Loop baby", 0, 0);
        Button.waitForAnyPress();
        LCD.clear();

        //Execution of line follower script
        for (int i = 0; i < 400; i++) {
            FollowLight();
        }

        //Turning the robot by 180 degrees
        (new TurnByDegreesCommand(180)).action();

        //Moving the robot backwards for a short amount of time
        //to compensate for the bad center of rotation
        (new MoveCommand(-20,300)).action();

        //Backtracking
        //filled command stack gets inverted and resulting command sequence is executed
        (history.inverse()).action();
    }

    /**
     * Final line follower script
     * Based on difference in light sensor readings
     * @throws InterruptedException
     */
    public static void FollowLight() throws InterruptedException {
        int valueRight = ldRight.getLightValue();
        int valueLeft = ldLeft.getLightValue();
        AbstractCommand current;

        /**
         * If both values are equal, both wheels receive the default power value.
         *
         * If the left (right) sensor reading is lower than the right (left),
         * the left (right) wheel's power is decreased by 2 every time window (50ms)
         * until either 0 has been hit
         * or a different condition has been met and power returns to the default value.
         */
        if (valueLeft < valueRight) {
            current = new MoveCommand(defaultPower, mRight.getPower() - 1 < 0 ? 0 : mRight.getPower() - 2, sleep);

        } else if (valueLeft > valueRight) {
            current = new MoveCommand(mRight.getPower() - 1 < 0 ? 0 : mRight.getPower() - 2, defaultPower, sleep);
        } else {
            current = new MoveCommand(defaultPower, defaultPower, sleep);
        }
        //Newly created command gets added to history and is executed
        history.add(current);
        current.action();

        /*
        int powerRight = 85 - valueLeft;
        powerRight /= 2;
        mRight.setPower(powerRight);
        int powerLeft = 85 - valueRight;
        powerLeft /= 2;
        mLeft.setPower(powerLeft);
        LCD.drawString("L: " + valueLeft + " R: " + valueRight, 0, 0);
        */
    }
}