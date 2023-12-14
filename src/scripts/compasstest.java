package scripts;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import settings.Settings;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;

public class compasstest {
    public static void main(String[] args) throws InterruptedException {
        Button.waitForAnyPress();
        turn(180);
        LCD.clear();
        /*while(true) {
            LCD.drawString(String.valueOf(Settings.compass.getDegrees()),0,0);
        }*/
    }

    public static void turn(int degree) throws InterruptedException {
        final int cycles = 2;
        final int abweichung = 1;
        float start = filteredStart();
        float end = (start + degree) % 360;
        int i = 0;
        //solange unterschied größer 10°
        Settings.mLeft.setPower(-15);
        Settings.mRight.setPower(15);
        while(i<cycles) {
            if(Math.abs(end - Settings.compass.getDegrees()) < abweichung) {
                i++;
            } else {
                i = 0;
            }
            LCD.drawString("start: " +start + "\n end: " + end + "\n" + Settings.compass.getDegrees(),0,0);
        }
        Settings.mLeft.setPower(0);
        Settings.mRight.setPower(0);
    }

    public static int filteredStart() {
        ArrayList<Float> start = new ArrayList<>();
        long s0 = System.currentTimeMillis() + 1000;
        while(s0 < System.currentTimeMillis()) {
            start.add(Settings.compass.getDegrees());
        }
        double start1 = start
                .stream()
                .mapToDouble(a -> a)
                .summaryStatistics()
                .getAverage();
        //TODO avg von arraylist nehmen
        return (int) start1 ;
    }
}
