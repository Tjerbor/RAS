package scripts;

import lejos.nxt.*;
import lejos.nxt.addon.CompassHTSensor;
import settings.Settings;

public class CompassCalibration {
    private static final NXTMotor mRight = new NXTMotor(MotorPort.A);
    private static final NXTMotor mLeft = new NXTMotor(MotorPort.C);
    private static final CompassHTSensor compass = Settings.compass;
    public static void main(String[] args) throws InterruptedException {
        Button.waitForAnyPress();
        compass.startCalibration();
        mRight.setPower(25);
        mLeft.setPower(15);
        Thread.sleep(60000);
        mRight.setPower(0);
        mLeft.setPower(0);
        compass.stopCalibration();
    }
}
