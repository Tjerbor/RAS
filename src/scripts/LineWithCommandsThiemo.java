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
    private static CommandSequence history = new CommandSequence();

    public static void main(String[] args) throws InterruptedException {
        //Calibration();
        //SetHighLow(50,0);
        LCD.drawString("Start the Loop baby", 0, 0);
        Button.waitForAnyPress();
        LCD.clear();

        /*while (true) {
            FollowLight();
        }
         */
        for (int i = 0; i < 400; i++) {
            FollowLight();
        }
        (new TurnByDegreesCommand(180)).action();
        (new MoveCommand(-20,300)).action();
        (history.inverse()).action();
    }

    public static void FollowLight() throws InterruptedException {
        int valueRight = ldRight.getLightValue();
        int valueLeft = ldLeft.getLightValue();
        AbstractCommand current;
        //
        if (valueLeft < valueRight) {
            current = new MoveCommand(defaultPower, mRight.getPower() - 1 < 0 ? 0 : mRight.getPower() - 2, sleep);

        } else if (valueLeft > valueRight) {
            current = new MoveCommand(mRight.getPower() - 1 < 0 ? 0 : mRight.getPower() - 2, defaultPower, sleep);
        } else {
            current = new MoveCommand(defaultPower, defaultPower, sleep);
        }
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