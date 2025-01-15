import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A representation of Listeria
 *
 * @Iffat Siddick, Ekaterina Hunter
 * @version 12
 */
public class Listeria extends CellChangeColour
{
    //The original colour of Listeria
    public static final Color VERY_LIGHT_BLUE = new Color(51, 204, 255);
    //The colours Listeria can change to
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color VERY_DARK_BLUE = new Color(0, 0, 153);
    
    /**
    * Create a new lifeform.
    *
    * @param field The field currently occupied.
    * @param location The location within the field.
    */
    public Listeria(Field field, Location location, Color col) {
        super(field, location, col);
    }
    
    /**
    * This is how the Mycoplasma decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());

        if (isAlive()) {
            if (neighbours.size() > 6){
                setNextState(false);
            }
        }
        else if (!isAlive()) { 
            //if Listeria is dead and has at least 1 living Salmonella neighbour it will come back to life
            boolean hasSalmonellaNeighbour = false;
            int i = 0;
            while (hasSalmonellaNeighbour == false && i < neighbours.size()) {
                if(neighbours.get(i) instanceof Salmonella) {
                    hasSalmonellaNeighbour = true;
                }
                i = i + 1;
            }
            if ( (neighbours.size() >= 3 && neighbours.size() <= 5) || hasSalmonellaNeighbour){
                setNextState(true);
                recovered();
            }
            else {
                setNextState(false);
            }
        }
        changeColour();
    }
    
    /**
     * The rules for is the cell chnages colour or not
     */
    public void changeColour()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        if (isAlive()) {
            if (neighbours.size() > 4){
                setColor(VERY_DARK_BLUE);
            }
            else if (neighbours.size() > 2){
                setColor(BLUE);
            }
        }
    }
    
    /**
     * This method sets the cell back to its original colour.
     * When it is cureed of disease.
     */
    public void recovered()
    {
        setColor(VERY_LIGHT_BLUE);
    }
}

