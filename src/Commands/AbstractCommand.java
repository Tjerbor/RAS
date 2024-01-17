package Commands;

public abstract class AbstractCommand {
    public abstract void action(boolean waitExtraTime) throws InterruptedException;

    public abstract AbstractCommand backwards();

    public abstract AbstractCommand inverse();

    public abstract AbstractCommand copy();
}
