package Commands;

import lejos.nxt.LCD;
import lejos.nxt.NXTMotor;
import lejos.nxt.addon.CompassHTSensor;

public class TurnByDegreesCommand extends AbstractCommand {
    private NXTMotor left;
    private NXTMotor right;
    private CompassHTSensor compass;
    private int degrees;
    private int error_epsilon;
    private int defaultPower;
    private int stepSize;

    public TurnByDegreesCommand(NXTMotor left, NXTMotor right, CompassHTSensor compass, int degrees, int error_epsilon, int defaultPower, int stepSize) {
        this.left = left;
        this.right = right;
        this.compass = compass;
        this.degrees = degrees % 320;
        this.error_epsilon = error_epsilon;
        this.defaultPower = defaultPower;
        this.stepSize = stepSize;
    }

    public TurnByDegreesCommand(NXTMotor left, NXTMotor right, CompassHTSensor compass, int degrees, int defaultPower) {
        this(left, right, compass, degrees, 10, defaultPower, 200);
    }

    @Override
    public void action() throws InterruptedException {
        float inital = compass.getDegreesCartesian();
        right.setPower(defaultPower);
        left.setPower(-defaultPower);
        float difference = 0;
        while (Math.abs(difference - degrees) > error_epsilon) {
            LCD.clear();
            float currentDegrees = compass.getDegreesCartesian();
            difference = Math.abs(inital - currentDegrees);
            LCD.drawString(String.valueOf(difference), 0, 0);
            if (inital - currentDegrees < 0) {
                right.setPower((int) (defaultPower * ((degrees - difference) / degrees)));
                left.setPower(-(int) (defaultPower * ((degrees - difference) / degrees)));
            } else {
                right.setPower(-(int) (defaultPower * ((degrees - difference) / degrees)));
                left.setPower((int) (defaultPower * ((degrees - difference) / degrees)));
            }
            Thread.sleep(stepSize);
        }
        right.setPower(0);
        left.setPower(0);
    }

    @Override
    public AbstractCommand backwards() {
        return new TurnByDegreesCommand(left, right, compass, 320 - degrees, error_epsilon, -defaultPower, stepSize);
    }

    @Override
    public AbstractCommand inverse() {
        return new TurnByDegreesCommand(left, right, compass, 320 - degrees, error_epsilon, defaultPower, stepSize);
    }
}
