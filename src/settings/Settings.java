package settings;

import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;

public class Settings {
    public static final NXTMotor mRight = new NXTMotor(MotorPort.A);
    public static final NXTMotor mLeft = new NXTMotor(MotorPort.C);
    public static final LightSensor ldRight = new LightSensor(SensorPort.S1, true);
    public static final LightSensor ldLeft = new LightSensor(SensorPort.S4, true);
    public static final CompassHTSensor compass = new CompassHTSensor(SensorPort.S2);
    public static int defaultPower = 25;
    public static final int error_epsilon = 10;
}
