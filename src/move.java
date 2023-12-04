import lejos.nxt.Motor;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class move {
    public static void main(String[] args) throws InterruptedException {
        LCD.drawString("Program 1", 0, 0);

        Button.waitForAnyPress();
        LCD.clear();
        System.out.println(Motor.A.getSpeed());
        Button.waitForAnyPress();
        Motor.A.forward();
        /*
        Motor.A.setSpeed();
        Motor.B.forward();
        LCD.drawString("FORWARD", 0, 0);
        Button.waitForAnyPress();
        LCD.drawString("BACKWARD", 0, 0);
        Motor.A.backward();
        Motor.B.backward();
        Button.waitForAnyPress();
        Motor.A.stop();
        Motor.B.stop();

         */
        for (int i = 0; i < 16; i++) {
            Motor.A.setSpeed(i*50);
            Motor.A.forward();
            Thread.sleep(500);
        }
        Thread.sleep(2000);
    }
}