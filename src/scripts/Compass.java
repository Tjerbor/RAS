package scripts;

import lejos.nxt.*;
import lejos.nxt.addon.CompassHTSensor;

public class Compass {
    private static final NXTMotor mRight = new NXTMotor(MotorPort.A);
    private static final NXTMotor mLeft = new NXTMotor(MotorPort.C);
    private static final CompassHTSensor compass = new CompassHTSensor(SensorPort.S2);
    static int defaultPower = 30;
    static float multiplicatorLeft = 1f;
    static float multiplicatorRight = 1;//.1f; //gleicht Fehler der Motoren aus
    static int minPower = 13;

    private static final double error_epsilon = 0.0;

    public static void main(String[] args) throws InterruptedException {
        boolean test = false;
        Button.waitForAnyPress();
        if (!test) {
            mRight.setPower((int) (defaultPower * multiplicatorRight));
            mLeft.setPower((int) (defaultPower * multiplicatorLeft));
            Thread.sleep(2000);
        }
        turn(test);
        //testCompass(1000);
        if (!test) {
            mRight.setPower((int) (defaultPower * multiplicatorRight));
            mLeft.setPower((int) (defaultPower * multiplicatorLeft));
            Thread.sleep(2000);
        }
    }

    private static void testCompass(int waitTime)throws InterruptedException{
        while(true){
            mRight.setPower(0);
            mLeft.setPower(0);
            Thread.sleep(waitTime);
            LCD.drawString("1: " + (compass.getDegreesCartesian()), 0, 0);
            //Threat.sleep(waitTime);

            Thread.sleep(waitTime);
            LCD.drawString("2: " + (compass.getDegreesCartesian())+ " ", 0, 1);
            //Thread.sleep(waitTime);

            Thread.sleep(waitTime);
            LCD.drawString("3: " + (compass.getDegreesCartesian()), 0, 2);
            //Thread.sleep(waitTime);

            Button.waitForAnyPress();
            mRight.setPower((int) (defaultPower * multiplicatorRight));
            mLeft.setPower(-(int) (defaultPower * multiplicatorLeft));
            Thread.sleep(waitTime);
        }
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
                        mRight.setPower(-((int) (power * multiplicatorRight)));
                        mLeft.setPower(((int) (power * multiplicatorLeft)));
                    } else {
                        mRight.setPower(((int) (power * multiplicatorRight)));
                        mLeft.setPower(-((int) (power * multiplicatorLeft)));
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
