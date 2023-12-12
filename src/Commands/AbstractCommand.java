package commands;

public abstract class AbstractCommand {
    public abstract void action() throws InterruptedException;

    public abstract AbstractCommand backwards();

    public abstract AbstractCommand inverse();

    public abstract AbstractCommand copy();
}
