package Commands;

import java.util.function.Consumer;

public abstract class AbstractCommand {
    abstract void action() throws InterruptedException;
    abstract AbstractCommand inverse();
    abstract AbstractCommand sequenceInverse();
}
