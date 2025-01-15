import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A representation of Salmonella
 *
 * @Iffat Siddick, Ekaterina Hunter
 * @version 12
 */
public class Salmonella extends CellChangeColour
{
    //The original colour of Salmonella
    public static final Color VERY_LIGHT_RED = new Color(255, 102, 102);
    //The colours Salmonella can change to
    public static final Color RED = new Color(255, 0, 0);
    public static final Color DARK_RED = new Color(204, 0, 0);
    public static final Color VERY_DARK_RED = new Color(153, 0, 0);
    
    /**
     * Create a new lifeform1.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
        
    public Salmonella(Field field, Location location, Color col) {
        super(field, location, col);
    }
    
    /**
    * This is how the lifeform decides if it's alive or not
    */
    public void act() 
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        if (neighbours.size() > 6) {
            setNextState(false);
        }
        else if(neighbours.size() > 4) {
            setNextState(true);
        }
        else if(neighbours.size() > 2) {
            setNextState(false);
        }
        else{
            setNextState(true);
        }
        
        if (!isAlive()) {
            //if Salmonella is dead and has at least 1 living Listeria neighbour it will come back to life
            boolean hasListeriaNeighbour = false;
            int i = 0;
            while (hasListeriaNeighbour == false && i < neighbours.size()) {
                if(neighbours.get(i) instanceof Listeria) {
                    hasListeriaNeighbour = true;
                }
                i = i + 1;
            }
            if (hasListeriaNeighbour || neighbours.size() == 3) {
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
     * The rules for is the cell changes colour or not
     */
    public void changeColour()
    {
        if (isAlive()) {
            if (getLifespan() > 10){
                setColor(RED);
            }
            else if (getLifespan() > 20){
                //not much visual difference between pink and megenta
                setColor(DARK_RED);
            }
            else if (getLifespan() > 30){
                setColor(VERY_DARK_RED);
            }
        }
    }
    
    /**
     * This method sets the cell back to its 0riginal colour.
     * When it is cureed of disease.
     */
    public void recovered()
    {
        setColor(VERY_LIGHT_RED);
    }
}
