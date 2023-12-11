package Commands;

import lejos.nxt.NXTMotor;

public class MoveCommand extends AbstractCommand{
    private NXTMotor left;
    private NXTMotor right;
    private int powerLeft;
    private int powerRight;
    private int stepSize;


    public MoveCommand(NXTMotor left, NXTMotor right, int powerLeft, int powerRight, int stepSize) {
        this.left = left;
        this.right = right;
        this.powerLeft = powerLeft;
        this.powerRight = powerRight;
        this.stepSize = stepSize;
    }

    public MoveCommand(NXTMotor left, NXTMotor right, int power, int stepSize) {
        this(left,right,power,power,stepSize);
    }

    @Override
    public void action() throws InterruptedException {
        left.setPower(powerLeft);
        right.setPower(powerRight);
        Thread.sleep(stepSize);
        left.setPower(0);
        right.setPower(0);
    }

    @Override
    public AbstractCommand backwards() {
        return new MoveCommand(left,right,-powerLeft,-powerRight,stepSize);
    }

    @Override
    public AbstractCommand inverse() {
        return this;
    }

    @Override
    public AbstractCommand copy() {
        return new MoveCommand(left,right,powerLeft,powerRight,stepSize);
    }
}
