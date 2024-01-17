package scripts;

import Commands.MoveCommand;
import lejos.nxt.*;
import lejos.nxt.addon.CompassHTSensor;

import static settings.Settings.GetAdjustedPower;

public class Compass {
    private static final NXTMotor mRight = new NXTMotor(MotorPort.A);
    private static final NXTMotor mLeft = new NXTMotor(MotorPort.C);
    private static final CompassHTSensor compass = new CompassHTSensor(SensorPort.S2);
    static int defaultPower = 30;
    static int minPower = 13;

    private static final double error_epsilon = 0.0;

    public static void main(String[] args) throws InterruptedException {

        testCompass();

        /*boolean test = false;
        Button.waitForAnyPress();
        if (!test) {
            mRight.setPower(GetAdjustedPower(defaultPower,true));
            mLeft.setPower(GetAdjustedPower(defaultPower,false));
            Thread.sleep(2000);
        }
        turn(test);
        //testCompass(1000);
        if (!test) {
            mRight.setPower(GetAdjustedPower(defaultPower,true));
            mLeft.setPower(GetAdjustedPower(defaultPower,false));
            Thread.sleep(2000);
        }*/
    }

    private static void testCompass() throws InterruptedException{

        (new MoveCommand(20,2000)).action();
        turn(false);
        (new MoveCommand(20,2000)).action();
    }

    public static float GetTarget(float initial){
        float target = (initial - 180);
        if(target < 0){
            target += 360;
        }
        return target;
    }

    public static void turn(boolean test) throws InterruptedException {
        mRight.setPower(0);
        mLeft.setPower(0);
        Thread.sleep(500);
        float initial = compass.getDegreesCartesian();
        float target = GetTarget(initial);

        float difference = 180;
        float current = initial;
        float currentStanding = initial;
        //while (currentStanding != target)
        {
            mRight.setPower(0);
            mLeft.setPower(0);
            Thread.sleep(500);
            currentStanding = compass.getDegreesCartesian();
            current = currentStanding;
            while (current != target) {
                LCD.clear();
                boolean turnRight = false;
                current = compass.getDegreesCartesian();
                if(target > 180){
                    if(current > initial && current < target) {
                        turnRight = true;
                    }
                }
                else{
                    if(current > initial || current < target) {
                        turnRight = true;
                    }
                }
                if(turnRight){
                    difference = target - current;
                }
                else {
                    difference = current - target;
                }
                if (difference < 0){
                    difference += 360;
                }

                float power = defaultPower * (difference / 90);
                power = power > defaultPower ? defaultPower : power;
                power = power < minPower ? minPower : power;

                LCD.drawString("difference:" + (difference), 0, 0);
                LCD.drawString("current:" + (current)+ " ", 0, 1);
                LCD.drawString("in:" + (initial) + " tar:" + (target), 0, 2);
                if(!test){
                    if (turnRight) {
                        mRight.setPower(-GetAdjustedPower(power,true));
                        mLeft.setPower(GetAdjustedPower(power,false));
                    } else {
                        mRight.setPower(GetAdjustedPower(power,true));
                        mLeft.setPower(-GetAdjustedPower(power,false));
                    }
                }
                Thread.sleep(50);
            }
        }

        mRight.setPower(0);
        mLeft.setPower(0);
        while (test){
            Thread.sleep(10000);
        }
    }

    private static void turnOld() throws InterruptedException {
        mRight.setPower(0);
        mLeft.setPower(0);
        Thread.sleep(500);
        float initial = compass.getDegreesCartesian();
        float initialMinusCurrent = 180;
        //mRight.setPower(defaultPower);
        //mLeft.setPower(-defaultPower);
        float difference = 0;
        while (Math.abs(difference - 180) > error_epsilon) {
            LCD.clear();
            float currentDegrees = compass.getDegreesCartesian();
            difference = Math.abs(initial - currentDegrees);
            initialMinusCurrent = initial - currentDegrees;
            if(Math.abs(initial - currentDegrees - 360) < difference){
                difference = Math.abs(initial - currentDegrees - 360);
                initialMinusCurrent = initial - currentDegrees - 360;
            }
            if(Math.abs(initial - currentDegrees + 360) < difference){
                difference = Math.abs(initial - currentDegrees + 360);
                initialMinusCurrent = initial - currentDegrees + 360;
            }
            float power = defaultPower * ((180 - difference) / 90);
            power = power > defaultPower ? defaultPower : power;
            power = power < minPower ? minPower : power;

            LCD.drawString(String.valueOf(difference) + " " + (initialMinusCurrent) + " " + (currentDegrees), 0, 0);
            /*if (initialMinusCurrent > 0) {
                mRight.setPower((int) (power));
                mLeft.setPower(-(int) (power));
            } else {
                mRight.setPower(-(int) (power));
                mLeft.setPower((int) (power));
            }*/
            Thread.sleep(50);
        }
        mRight.setPower(0);
        mLeft.setPower(0);
    }
}
