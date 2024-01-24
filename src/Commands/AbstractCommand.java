package Commands;

/**
 * Upper class of all commands
 */
public abstract class AbstractCommand {
    /**
     * Internal logic in inherted classes
     * @throws InterruptedException
     */
    public abstract void action() throws InterruptedException;

    /**
     * Returns a derived command that executes the original's  internal logic fully backwards
     */
    public abstract AbstractCommand backwards();

    /**
     * Returns a derived command that executes the original's internal logic inverted
     * based on the robot having turned 180 degrees in a previous step
     */
    public abstract AbstractCommand inverse();

    /**
     * Returns a new command object with all parameters copied
     */
    public abstract AbstractCommand copy();
}
