import lejos.nxt.*;
import lejos.nxt.addon.CompassHTSensor;

public class Compass {
    private static final NXTMotor mRight = new NXTMotor(MotorPort.A);
    private static final NXTMotor mLeft = new NXTMotor(MotorPort.C);
    private static final CompassHTSensor compass = new CompassHTSensor(SensorPort.S2);
    static int defaultPower = 25;
    public static void main(String[] args) throws InterruptedException {
        Button.waitForAnyPress();
        mRight.setPower(defaultPower);
        mLeft.setPower(defaultPower);
        Thread.sleep(2000);
        turn();
        mRight.setPower(defaultPower);
        mLeft.setPower(defaultPower);
        Thread.sleep(2000);
    }

    private static void turn() throws InterruptedException {
        float inital = compass.getDegreesCartesian();
        mRight.setPower(defaultPower);
        mLeft.setPower(-defaultPower);
        float difference = 0;
        while(difference < 170 || difference > 190){
            LCD.clear();
            difference = Math.abs(inital - compass.getDegreesCartesian());
            LCD.drawString(String.valueOf(difference), 0, 0);
            mRight.setPower((int) (defaultPower*((180-difference)/180)));
            mLeft.setPower(-(int) (defaultPower*((180-difference)/180)));
            Thread.sleep(200);
        }
        mRight.setPower(0);
        mLeft.setPower(0);

    }
}
