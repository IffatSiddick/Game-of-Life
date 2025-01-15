import java.awt.Color;

/**
 * This is a subclass for all subclasses of Cell that can change their colour during the simulation
 * as part of their rules set.
 * This does not inlcude changing colour due to becoming diseased or dead.
 *
 * This makes the program more extendable if more Cell subclasses are added in the future.
 *
 * @Iffat Siddick
 * @ version 3
 */
public abstract class CellChangeColour extends Cell
{
    /**
     * Constructor for objects of class CellChangeColour
     */
    public CellChangeColour(Field field, Location location, Color col)
    {
        super(field, location, col);
    }
    
    abstract protected void act();
    
    abstract protected void changeColour();
}
