package scripts;

import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;

public class CompassCalibration {
    private static final NXTMotor mRight = new NXTMotor(MotorPort.A);
    private static final NXTMotor mLeft = new NXTMotor(MotorPort.C);
    private static final CompassHTSensor compass = new CompassHTSensor(SensorPort.S2);
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
