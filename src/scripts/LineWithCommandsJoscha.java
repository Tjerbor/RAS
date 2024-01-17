package scripts;

import Commands.AbstractCommand;
import Commands.CommandSequence;
import Commands.MoveCommand;
import lejos.nxt.*;
import lejos.nxt.addon.CompassHTSensor;
import settings.Settings;

import static settings.Settings.GetAdjustedPower;

public class LineWithCommandsJoscha {
    private static final LightSensor ldRight = Settings.ldRight;
    private static final LightSensor ldLeft = Settings.ldLeft;
    private static final NXTMotor mRight = Settings.mRight;
    private static final NXTMotor mLeft = Settings.mLeft;
    private static int defaultPower = Settings.defaultPower;
    private static int sleep = 50;
    private static CommandSequence history = new CommandSequence();
    private static final CompassHTSensor compass = new CompassHTSensor(SensorPort.S2);

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
        float compassInitial = compass.getDegreesCartesian();
        float difference = 0;
        boolean turnedAround = false;

        while (difference > 3 || !turnedAround) {

            FollowLight();

            float compassCurrent = compass.getDegreesCartesian();
            difference = (compassInitial > compassCurrent) ? compassInitial - compassCurrent : compassCurrent - compassInitial;
            if(difference > 180){
                difference = 360 - difference;
            }

            if(!turnedAround && difference > 175){
                turnedAround = true;
            }

            LCD.clear();
            LCD.drawString("difference:" + (difference), 0, 0);
            LCD.drawString("turnedAround:" + (turnedAround)+ " ", 0, 1);
        }

        //(new TurnByDegreesCommand(180)).action();
        Compass.turn(false);

        mRight.setPower(-GetAdjustedPower(defaultPower,true));
        mLeft.setPower(-GetAdjustedPower(defaultPower,false));
        Thread.sleep(1000);
        mRight.setPower(0);
        mLeft.setPower(0);
        Thread.sleep(1000);
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