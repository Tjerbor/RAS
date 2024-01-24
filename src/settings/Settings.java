package settings;

import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;

/**
 * Static constants to minimize code duplocations
 */
public class Settings {
    public static final NXTMotor mRight = new NXTMotor(MotorPort.A);
    public static final NXTMotor mLeft = new NXTMotor(MotorPort.C);
    public static final LightSensor ldRight = new LightSensor(SensorPort.S1, true);
    public static final LightSensor ldLeft = new LightSensor(SensorPort.S4, true);

    public static final CompassHTSensor compass = new CompassHTSensor(SensorPort.S2);

    /**
     * Default power for NXT motors
     */
    public static int defaultPower = 25;

    /**
     * Deprecated
     */
    public static final int error_epsilon = 10;

    static float multiplicatorLeft = 1f;
    static float multiplicatorRight = 1f; //gleicht Fehler der Motoren aus

    public static int GetAdjustedPower(float power, boolean right){
        float multi = right ? multiplicatorRight : multiplicatorLeft;

        return (int) (power * multi);
    }
}
