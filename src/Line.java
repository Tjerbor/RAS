import lejos.nxt.*;

public class Line {
    private static final LightSensor ldRight = new LightSensor(SensorPort.S1, true);
    private static final LightSensor ldLeft = new LightSensor(SensorPort.S4, true);
    private static final NXTMotor mRight = new NXTMotor(MotorPort.A);
    private static final NXTMotor mLeft = new NXTMotor(MotorPort.C);

    static int defaultPower = 25;

    public static void main(String[] args) throws InterruptedException {
        //Calibration();
        //SetHighLow(50,0);
        LCD.drawString("Start Loop", 0, 0);
        Button.waitForAnyPress();
        LCD.clear();

        mRight.setPower(defaultPower);
        mLeft.setPower(defaultPower);

        while (true) {
            FollowLight();
            Thread.sleep(50);
        }
    }

    public static void FollowLight() {
        int valueRight = ldRight.getLightValue();
        int valueLeft = ldLeft.getLightValue();

        if (valueLeft < valueRight) {
            mRight.setPower(mRight.getPower() - 1 < 0 ? 0 : mRight.getPower() - 2);
            mLeft.setPower(defaultPower);
        } else if (valueLeft > valueRight) {
            mRight.setPower(defaultPower);
            mLeft.setPower(mLeft.getPower() - 1 < 0 ? 0 : mLeft.getPower() - 2);
        } else {
            mRight.setPower(defaultPower);
            mLeft.setPower(defaultPower);
        }

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