import lejos.nxt.*;

public class Line
{
    private static final LightSensor ldRight = new LightSensor(SensorPort.S1, true);
    private static final LightSensor ldLeft = new LightSensor(SensorPort.S4, true);
    private static final NXTMotor mRight = new NXTMotor(MotorPort.A);
    private static final NXTMotor mLeft = new NXTMotor(MotorPort.C);

    static int maxPower = 30;
    static int changeRate = 6;
    static int powerLeft = 5;
    static int powerRight = 5;

    public static void main(String[]args) throws InterruptedException
    {
        LCD.drawString("Start Loop",0,0);
        Button.waitForAnyPress();
        LCD.clear();

        mRight.setPower(powerRight);
        mLeft.setPower(powerLeft);
        while(true)
        {
            FollowLine();
            Thread.sleep(50);
        }
    }

    public static void FollowLine() {
        int valueRight = ldRight.getLightValue();
        int valueLeft = ldLeft.getLightValue();

        if (valueLeft - valueRight < 0)
        {
            powerRight -= changeRate;
            powerLeft = maxPower;
        }
        else if (valueRight - valueLeft < 0)
        {
            powerRight = maxPower;
            powerLeft -= changeRate;
        }
        else
        {
            powerRight = maxPower;
            powerLeft = maxPower;
        }

        powerRight = (powerRight > maxPower) ? maxPower : powerRight;
        powerRight = (powerRight < -maxPower/2) ? -maxPower/2 : powerRight;
        powerLeft = (powerLeft > maxPower) ? maxPower : powerLeft;
        powerLeft = (powerLeft < -maxPower/2) ? -maxPower/2 : powerLeft;
        mRight.setPower(powerRight);
        mLeft.setPower(powerLeft);
    }
}
