import lejos.nxt.Button;
import lejos.nxt.LCD;

public class ssss {
  public static void main(String[] args) throws Exception {
    while (true) {
      LCD.drawString("Free RAM:", 0, 0);
      LCD.drawInt((int) Runtime.getRuntime().freeMemory(), 6, 9, 0);
      Thread.sleep(2000);
    }
  }
}